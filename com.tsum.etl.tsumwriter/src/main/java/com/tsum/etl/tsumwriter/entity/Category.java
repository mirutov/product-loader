package com.tsum.etl.tsumwriter.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Класс-сущность для представления категории продукта
 * Created by aam on 11.03.17.
 */
@Entity
@Table(name = "BrandTag")
public class Category {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "externalId")
    private String externalId;

    protected Category() {

    }

    public long getId() {
        return id;
    }

    public String getExternalId() {
        return externalId;
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("Brand");
        sBuilder.append("\nid: ");
        sBuilder.append(id);
        sBuilder.append("\nexternalId: ");
        sBuilder.append(externalId);
        return sBuilder.toString();
    }
}
