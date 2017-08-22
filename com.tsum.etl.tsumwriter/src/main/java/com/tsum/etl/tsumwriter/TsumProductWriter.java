package com.tsum.etl.tsumwriter;

import com.tsum.etl.common.ProductWriter;
import com.tsum.etl.tsumwriter.entity.Brand;
import com.tsum.etl.tsumwriter.entity.Category;
import com.tsum.etl.common.model.Product;
import com.tsum.etl.tsumwriter.service.BrandService;
import com.tsum.etl.tsumwriter.service.CategoryService;
import com.tsum.etl.tsumwriter.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс, сохраняющий информацию о продуктах в БД ЦУМа
 * Реализует интерфейс {@link ProductWriter}
 * Created by aam on 15.03.17.
 */
@Service
public class TsumProductWriter implements ProductWriter {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;

    private static final Logger logger = LoggerFactory.getLogger(ProductWriter.class);

    public Collection<Product> saveProducts(Collection<Product> products) {
        throw new UnsupportedOperationException();
    }

    @Transactional
    public Collection<Product> saveProductsToCategory(Collection<Product> products, String categoryExternalId) {
        LinkedList<Product> ret = new LinkedList<>();
        Category category = categoryService.findByExternalId(categoryExternalId);
        if (category == null) {
            logger.warn("Category " + categoryExternalId + " not found");
            return ret;
        }
        Timestamp updateTimestamp = new Timestamp(System.currentTimeMillis());
        List<com.tsum.etl.tsumwriter.entity.Product> dbProducts = new LinkedList<>();
        for (Product product : products) {
            Timestamp startTimestamp = new Timestamp(System.currentTimeMillis());
            com.tsum.etl.tsumwriter.entity.Product dbProduct = productService.findById(product.getId());
            if (dbProduct != null) {
                dbProduct = updateDBProduct(dbProduct, product, category, updateTimestamp);
            } else {
                dbProduct = createDBProduct(product, category, updateTimestamp);
            }
            dbProducts.add(dbProduct);
            ret.add(product);
            Timestamp endTimestamp = new Timestamp(System.currentTimeMillis());
            logger.debug("Processign a product {} finished. {} ms", product.getId(),
                    endTimestamp.getTime() - startTimestamp.getTime());
        }
        try {
            logger.debug("Start saving for category {}", categoryExternalId);
            productService.save(dbProducts);
            logger.debug("Finish saving for category {}", categoryExternalId);
        } catch (org.hibernate.exception.LockAcquisitionException ex) {
            logger.warn("Deadlock was detected for category {} ", categoryExternalId, ex);
            try {
                Thread.sleep(5000);
                productService.save(dbProducts);
            } catch (Exception finalEx) {
                throw new RuntimeException(finalEx);
            }
        }
        return ret;
    }

    @Transactional
    public void deleteObsoletedProducts(Collection<String> categoriesExternalIds, Timestamp timestamp) {
        productService.deleteObsoleted(categoriesExternalIds, timestamp);
    }

    private com.tsum.etl.tsumwriter.entity.Product updateDBProduct(com.tsum.etl.tsumwriter.entity.Product dbProduct, Product product,
                                                                    Category category, Timestamp updateTimestamp) {

        dbProduct.setName(product.getName());
        dbProduct.setSizes(product.getSizes());
        dbProduct.setPrice(product.getPrice());
        dbProduct.setNewPrice(product.getNewPrice());
        dbProduct.setActionEnabled(product.isActionEnabled());
        dbProduct.setActionDetails(product.getActionDetails());
        dbProduct.setSmallPhotoURL(product.getSmallPhotoURL());
        dbProduct.setLargePhotoURL(product.getLargePhotoURL());
        dbProduct.setCategory(category);
        Brand brand = brandService.findByName(product.getBrand());
        dbProduct.setBrand(brand);
        dbProduct.setUpdateTimestamp(updateTimestamp);
        return dbProduct;
    }

    private com.tsum.etl.tsumwriter.entity.Product createDBProduct(Product product, Category category,
                                                                    Timestamp updateTimestamp) {
        com.tsum.etl.tsumwriter.entity.Product dbProduct = new com.tsum.etl.tsumwriter.entity.Product();
        dbProduct.setId(product.getId());
        dbProduct.setName(product.getName());
        dbProduct.setSizes(product.getSizes());
        dbProduct.setPrice(product.getPrice());
        dbProduct.setNewPrice(product.getNewPrice());
        dbProduct.setActionEnabled(product.isActionEnabled());
        dbProduct.setActionDetails(product.getActionDetails());
        dbProduct.setSmallPhotoURL(product.getSmallPhotoURL());
        dbProduct.setLargePhotoURL(product.getLargePhotoURL());
        dbProduct.setCategory(category);
        Brand brand = brandService.findByName(product.getBrand());
        dbProduct.setBrand(brand);
        dbProduct.setUpdateTimestamp(updateTimestamp);
        return dbProduct;
    }
}
