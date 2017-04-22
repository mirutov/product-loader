package com.tsum.etl.loader;

import com.tsum.etl.common.ETLManager;
import com.tsum.etl.common.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.*;

/**
 * Класс, запускающий процесс ETL
 * Created by aam on 08.03.17.
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})
@ComponentScan(basePackages = {
        "com.tsum.etl.vkreader",
        "com.tsum.etl.tsumwriter",
        "com.tsum.etl.manager"})
public class ProductLoader {
    private static final String ResultsLogger = "RESULTS";
    private static final Logger logger = LoggerFactory.getLogger(ProductLoader.class);

    public static void main(String[] args) throws InterruptedException {
        try {
            ConfigurableApplicationContext context = SpringApplication.run(ProductLoader.class);
            etl(context, args);
        } catch (Exception ex) {
            logger.error("Error: ", ex);
        }
    }

    public static void etl(ConfigurableApplicationContext ctx, String[] args) {
        try {
            ETLManager etl = ctx.getBean(ETLManager.class);
            Map<String, Collection<Product>> results = etl.etlByCategories(getExternalCategoriesIds(args));
            printResult(results);

        } catch (Exception ex) {
            logger.error("Error: ", ex);
        }
    }

    private static Collection<String> getExternalCategoriesIds(String[] args) {

        if (args.length == 0) {
            throw new IllegalArgumentException("Categories list for ETL must be set");
        }
        return Arrays.asList(args);
    }

    private static void printResult(Map<String, Collection<Product>> results) {
        Logger logger = LoggerFactory.getLogger(ResultsLogger);
        for (String externalCategory : results.keySet()) {
            logger.info("externalCategoryId: {}", externalCategory);
            for (Product product : results.get(externalCategory)) {
                logger.info(String.valueOf(product.getId()));
            }
        }
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
