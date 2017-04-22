package com.tsum.etl.tsumwriter.service.impl;

import com.tsum.etl.tsumwriter.entity.Category;
import com.tsum.etl.tsumwriter.repository.CategoryRepository;
import com.tsum.etl.tsumwriter.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Класс для работы с категориями продуктов
 * Реализует интерфейс {@link CategoryService}
 * Created by aam on 11.03.17.
 */

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category findByExternalId(String externalId) {
        return categoryRepository.findByExternalId(externalId);
    }

    @Override
    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }
}
