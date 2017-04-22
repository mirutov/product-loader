package com.tsum.etl.manager;

import com.tsum.etl.common.ETLManager;
import com.tsum.etl.common.ProductReader;
import com.tsum.etl.common.ProductWriter;
import com.tsum.etl.common.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Класс, управляющий процессом ETL
 * Реализует {@link ETLManager}
 */
@Service
public class TsumEtlManager implements ETLManager {
    private static final Logger logger = LoggerFactory.getLogger(ETLManager.class);
    @Value("${manager.core-pool-size}")
    private int corePoolSize;
    @Value("${manager.max-pool-size}")
    private int maxPoolSize;
    @Value("${manager.queue-capacity}")
    private int queueCapacity;

    @Autowired
    private ProductReader reader;
    @Autowired
    private ProductWriter writer;

    @Override
    public Collection<Product> etlByProducts(Collection<String> productsIds) throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, Collection<Product>> etlByCategories(Collection<String> categoryExterlanIds)
            throws InterruptedException {
        if (categoryExterlanIds == null || categoryExterlanIds.size() < 1) {
            throw new IllegalArgumentException("exterlanCategoriesIds must contain at least 1 item");
        }

        if (categoryExterlanIds.size() > queueCapacity) {
            throw new IllegalArgumentException("Count of categories must not be more " + queueCapacity);
        }

        ThreadPoolTaskExecutor tp = new ThreadPoolTaskExecutor();
        tp.setCorePoolSize(corePoolSize);
        tp.setMaxPoolSize(maxPoolSize);
        tp.setQueueCapacity(queueCapacity);
        tp.setWaitForTasksToCompleteOnShutdown(true);
        tp.initialize();

        HashMap<String, Future<Collection<Product>>> futures = new HashMap<String, Future<Collection<Product>>>();

        Timestamp updateTimestamp = new Timestamp(System.currentTimeMillis());

        CountDownLatch latch = new CountDownLatch(categoryExterlanIds.size());

        for (String categoryExterlanId: categoryExterlanIds) {
            Future<Collection<Product>> tmp = tp.submit(getTask(reader, writer, categoryExterlanId, latch));
            futures.put(categoryExterlanId, tmp);
        }
        latch.await();
        Collection<String> processedCategoryExternalIds = new LinkedList<String>();
        Map<String, Collection<Product>> result = new HashMap<String, Collection<Product>>();

        for (String categoryExterlanId : futures.keySet()) {
            Collection<Product> products = null;
            try {
                products = futures.get(categoryExterlanId).get();
            } catch (ExecutionException ex) {}
            if (products != null) {
                processedCategoryExternalIds.add(categoryExterlanId);
                result.put(categoryExterlanId, products);
            }
        }
        tp.shutdown();
        try {
            writer.deleteObsoletedProducts(processedCategoryExternalIds, updateTimestamp);
        } catch (Exception ex) {
            logger.error("Error deleting obolute products", ex);
        }
        return result;
    }

    private Callable<Collection<Product>> getTask (ProductReader reader, ProductWriter writer,
                                                          String categotyExetrlanId, CountDownLatch latch) {
        return () -> {
            Collection<Product> products = null;
            try {
                products = reader.getProductsByCategory(categotyExetrlanId);
                products = writer.saveProductsToCategory(products, categotyExetrlanId);
            } catch (Exception ex) {
                logger.error("Error processing a category {}: ", categotyExetrlanId, ex);
                products = null;
            } finally {
                latch.countDown();
                return products;
            }

        };
    }
}