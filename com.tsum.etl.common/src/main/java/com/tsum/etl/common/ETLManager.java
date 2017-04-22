package com.tsum.etl.common;

import com.tsum.etl.common.model.Product;

import java.util.Collection;
import java.util.Map;

/**
 * Интерфейс ETL-сервиса
 * Created by aam on 19.03.17.
 */
public interface ETLManager {
    /**
     * Метод обеспечивает извлечение информации о продуктах из хранилища-источника, преобразование
     * описания продуктов к виду, пригодному для загрузки в хранилище-приемник и загрузку информаиции о
     * продуктах в хранилище-приемник
     * @param productsIds - коллекция идентификаторов продуктов в хранилище-источнике
     * @return - коллекция загруженных продуктов
     * @throws InterruptedException
     */
    Collection<Product> etlByProducts(Collection<String> productsIds) throws InterruptedException;;


    /**
     * Метод обеспечивает извлечение информации о продуктах из хранилища-источника, преобразование
     * описания продуктов к виду, пригодному для загрузки в хранилище-приемник и загрузку информаиции о
     * продуктах в хранилище-приемник
     * @param exterlanCategoriesIds - коллекция идентификаторов категорий продуктов в хранилище-источнике
     * @return - - коллекция загруженных продуктов, сгруппированная по идентификаторам категорий в
     * хранилище-источнике
     * @throws InterruptedException
     */
    Map<String, Collection<Product>> etlByCategories(Collection<String> exterlanCategoriesIds)
            throws InterruptedException;
}
