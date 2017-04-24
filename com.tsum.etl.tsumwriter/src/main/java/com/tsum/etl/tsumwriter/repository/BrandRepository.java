package com.tsum.etl.tsumwriter.repository;

import com.tsum.etl.tsumwriter.entity.Brand;
import org.springframework.data.repository.CrudRepository;

/**
 * Интерфейс репозитория для работы с брендами
 * Created by aam on 16.03.17.
 */
public interface BrandRepository extends CrudRepository<Brand, String> {
    Brand findByName(String name);
}
