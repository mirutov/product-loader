package com.tsum.etl.tsumwriter.service;

import com.tsum.etl.tsumwriter.entity.Brand;

/**
 * Интерфейс для работы с брендами
 * Created by aam on 16.03.17.
 */
public interface BrandService {
    Brand findByName(String name);
    Iterable<Brand> findAll();
}
