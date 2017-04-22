package com.tsum.etl.tsumwriter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Класс-сущность для представления бренде
 * Created by aam on 15.03.17.
 */
@Entity
@Table(name = "Brand")
public class Brand {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    protected Brand() {

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("Brand");
        sBuilder.append("\nid: ");
        sBuilder.append(id);
        sBuilder.append("\nname: ");
        sBuilder.append(name);
        return sBuilder.toString();
    }
}
