package com.tsum.etl.tsumwriter.repository;

import com.tsum.etl.tsumwriter.entity.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.Collection;

/**
 * Интерфейс репозитория для работы с продуктами
 * Created by aam on 16.03.17.
 */
public interface ProductRepository extends CrudRepository<Product, Long> {
    @Modifying
    @Query(value="delete from Product p where p.updateTimestamp < ?2 and p.category in " +
            "(select id from Category where externalId in ?1)")
    void deleteObsoluted(Collection<String> categoriesExternalIds, Timestamp updateTimestamp);
}
