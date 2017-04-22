package com.tsum.etl.tsumwriter.service.impl;

/**
 * Created by aam on 16.03.17.
 */

import com.tsum.etl.tsumwriter.entity.Brand;
import com.tsum.etl.tsumwriter.repository.BrandRepository;
import com.tsum.etl.tsumwriter.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Класс для работы с брендами
 * Реализует интерфейс {@link BrandService}
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    BrandRepository brandRepository;

    @Override
    public Brand findByName(String name) {
        return brandRepository.findByName(name);
    }

    @Override
    public Iterable<Brand> findAll() {
        return brandRepository.findAll();
    }
}
