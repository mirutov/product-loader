package com.tsum.etl.tsumwriter.repository;

import com.tsum.etl.tsumwriter.entity.Category;
import org.springframework.data.repository.CrudRepository;

/**
 * Интерфейс репозитория для работы с категориями продуктов
 * Created by aam on 11.03.17.
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {
    Category findByExternalId(String id);
}
