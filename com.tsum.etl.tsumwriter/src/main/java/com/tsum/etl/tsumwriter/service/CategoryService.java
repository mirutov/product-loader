package com.tsum.etl.tsumwriter.service;

import com.tsum.etl.tsumwriter.entity.Category;

/**
 * Интерфейс для работы с категориями продуктов
 * Created by aam on 11.03.17.
 */
public interface CategoryService {
    Category findByExternalId(String externalId);
    Iterable<Category> findAll();
}
