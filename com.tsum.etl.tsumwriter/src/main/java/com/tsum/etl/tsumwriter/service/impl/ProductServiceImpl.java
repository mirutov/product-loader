package com.tsum.etl.tsumwriter.service.impl;

import com.tsum.etl.tsumwriter.entity.Product;
import com.tsum.etl.tsumwriter.repository.ProductRepository;
import com.tsum.etl.tsumwriter.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Класс для работы с продуктами
 * Реализует интерфейс {@link ProductService}
 * Created by aam on 16.03.17.
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public Product findById(long id) {
        return productRepository.findOne(id);
    }

    @Override
    public Collection<Product> findAll() {
        LinkedList<Product> products = new LinkedList<Product>();
        productRepository.findAll().forEach(products::add);
        return products;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Iterable<Product> save(Iterable<Product> products) {
        return productRepository.save(products);
    }

    @Override
    public void deleteObsoleted(Collection<String> categoriesExternalIds, Timestamp updateTimestamp) {

        productRepository.deleteObsoluted(categoriesExternalIds, updateTimestamp);
    }
}
