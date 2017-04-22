package com.tsum.etl.tsumwriter.entity;

import org.apache.commons.validator.routines.UrlValidator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Класс-сущность для представления продукта
 * Created by aam on 15.03.17.
 */
@Entity
@Table(name = "Product")
public class Product {
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "sizes")
    private String sizes;
    @Column(name = "price")
    private long price;
    @Column(name = "newPrice")
    private long newPrice;
    @Column(name = "actionEnabled")
    private boolean actionEnabled;
    @Column(name = "actionDetails")
    private String actionDetails;
    @Column(name = "smallPhotoURL")
    private String smallPhotoURL;
    @Column(name = "largePhotoURL")
    private String largePhotoURL;
    @Column(name = "updateTimestamp")
    private Timestamp updateTimestamp;
    @ManyToOne
    @JoinColumn(name = "brandId")
    private Brand brand;
    @ManyToOne
    @JoinColumn(name = "brandTagId")
    private Category category;

    public Product() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(long newPrice) {
        this.newPrice = newPrice;
    }

    public boolean isActionEnabled() {
        return actionEnabled;
    }

    public void setActionEnabled(boolean actionEnabled) {
        this.actionEnabled = actionEnabled;
    }

    public String getActionDetails() {
        return actionDetails;
    }

    public void setActionDetails(String actionDetails) {
        this.actionDetails = actionDetails;
    }

    public String getSmallPhotoURL() {
        return smallPhotoURL;
    }

    public void setSmallPhotoURL(String smallPhotoURL) {
        if (smallPhotoURL != "") {
            UrlValidator UrlValidator = new UrlValidator();
            if (!UrlValidator.isValid(smallPhotoURL)) {
                throw new IllegalArgumentException("smallPhotoURL must be a valid URL");
            }
        }
        this.smallPhotoURL = smallPhotoURL;
    }

    public String getLargePhotoURL() {
        return largePhotoURL;
    }

    public void setLargePhotoURL(String largePhotoURL) {
        if (largePhotoURL != "") {
            UrlValidator UrlValidator = new UrlValidator();
            if (!UrlValidator.isValid(largePhotoURL)) {
                throw new IllegalArgumentException("largePhotoURL must be a valid URL");
            }
        }
        this.largePhotoURL = largePhotoURL;
    }

    public Timestamp getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Timestamp updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("Product");
        sBuilder.append("\nid: ");
        sBuilder.append(id);
        sBuilder.append("\nname: ");
        sBuilder.append(name);
        sBuilder.append("\ncategory: ");
        sBuilder.append(category.toString());
        sBuilder.append("\nsizes: ");
        sBuilder.append(sizes);
        sBuilder.append("\nprice: ");
        sBuilder.append(price);
        sBuilder.append("\nnewPrice: ");
        sBuilder.append(newPrice);
        sBuilder.append("\nactionEnabled: ");
        sBuilder.append(actionEnabled);
        if (actionEnabled) {
            sBuilder.append("\nactionDetails: ");
            sBuilder.append(actionDetails);
        }
        sBuilder.append("\nbrand: ");
        sBuilder.append(brand.toString());
        sBuilder.append("\nsmallPhotoURL: ");
        sBuilder.append(smallPhotoURL);
        sBuilder.append("\nlargePhotoURL: ");
        sBuilder.append(largePhotoURL);
        return sBuilder.toString();
    }
}
