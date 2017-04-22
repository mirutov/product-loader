package com.tsum.etl.common;

import com.tsum.etl.common.model.Product;

import java.util.Collection;


/**
 * Интерфейс сервиса, считывающего информацию о продуктах из некоторого хранилища-источника
 */
public interface ProductReader {

    /**
     * Метод возвращает коллекцию продуктов по их идентификаторам в некоторой внешней системе
     * @param productsIds - коллекция идентификаторов продуктов во внешней системе
     * @return - коллекция продуктов
     */
    Collection<Product> getProducts(Collection<String> productsIds);

    /**
     * Метод возвращает коллекцию продуктов по идентификатору их категории в некоторой внешней системе
     *  @param categoryId - идентификатор категории продуктов в в некоторой внешней системе
     * @return  - коллекция продуктов
     * @throws Exception
     */
    Collection<Product> getProductsByCategory(String categoryId) throws Exception;
}