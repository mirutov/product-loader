package com.tsum.etl.tsumwriter.service;

import com.tsum.etl.tsumwriter.entity.Product;

import java.sql.Timestamp;
import java.util.Collection;

/**
 * Интерфейс для работы с продуктами
 * Created by aam on 16.03.17.
 */
public interface ProductService {
    Product findById(long id);
    Collection<Product> findAll();
    Product save(Product product);
    Iterable<Product> save(Iterable<Product> products);
    void deleteObsoleted(Collection<String> categoriesExternalIds, Timestamp updateTimestamp);
}
