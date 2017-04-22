package com.tsum.etl.common;

import com.tsum.etl.common.model.Product;

import java.sql.Timestamp;
import java.util.Collection;

/**
 * Интерфейс сервиса, записывающего информацию о продуктах в некоторое хранилище
 * Created by aam on 08.03.17.
 */
public interface ProductWriter {

    /**
     * Метод сохраняет ифорацию о продуктах в некоторое хранилище
     * @param products - коллекция продуктов, которые необходимо сохранить
     * @return - коллекция сохраненных продуктов
     */
    Collection<Product> saveProducts(Collection<Product> products);

    /**
     * Метод сохраняет ифорацию о продуктах в некоторое хранилище устанавливая им категорию,
     * которая соответствует значению параметра categoryExternalId
     * @param products -коллекция продуктов, которые необходимо сохранить
     * @param categoryExternalId - идентификатор категории, в котороую необходимо сохранить информацию о
     *                           продуктах
     * @return - коллекция сохраненных продуктов
     */
    Collection<Product> saveProductsToCategory(Collection<Product> products, String categoryExternalId);

    /**
     * Метод удаляет из хранилища инфорациб о продуктах из указанных категорий, которые были изменены
     * ранее значения параметра timestamp
     * @param categoriesExternalIds - список идентификаторов категорий
     * @param timestamp - время
     */
    void deleteObsoletedProducts(Collection<String> categoriesExternalIds, Timestamp timestamp);
}
