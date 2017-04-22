package com.tsum.etl.common.model;

import org.apache.commons.validator.routines.UrlValidator;

import java.util.HashMap;


/**
 *  Представление продукта
 */
public final class Product {
    /**
     *  Идентификатор товара
     */
    private int id;
    /**
     * Кадегория товара
     */
    private String category;
    /**
     *  Наименование товара
     */
    private String name;
    /**
     * Доступные размеры
     */
    private String sizes;
    /**
     * Цена
     */
    private int price;
    /**
     * Новая цена
     */
    private int newPrice;
    /**
     * Признак акции. Если = true, товав продаетя по акции
     */
    private boolean actionEnabled;
    /**
     * Детальное описание акции
     */
    private String actionDetails;

    /**
     * Бренд товара
     */
    private String brand;

    /**
     * URL к фото товара маленького размером
     */
    private String smallPhotoURL;
    /**
     * URL к фото товара больщого размера
     */
    private String largePhotoURL;

    private Product() {

    }

    /**
     * Метод преобразует строковое описание продукта в коллекцию вида {Имя параметра - Значение параметра}
     * @param productDescription - атрибуты продукта в виде строки
     * @return -  атрибуты продукта представленные в виде множества пар {Имя параметра - Значение параметра}
     */
    private static HashMap<ProductField, String> getProductsParams(String productDescription) {

        HashMap<ProductField, String> ret = new HashMap<ProductField, String>();
        String[] productDesc = productDescription.split("\n");
        for (String item : productDesc) {
            if (item.length() == 0)
                continue;;
            int colonIndex = item.indexOf(':');
            if (colonIndex != -1) {
                String prefix = item.toUpperCase().substring(0, item.indexOf(':'));

                if (ProductField.NAME.toString().equals(prefix)) {
                    ret.put(ProductField.NAME, item.substring(colonIndex+1).trim());

                } else if (ProductField.SIZES.toString().equals(prefix)) {
                    ret.put(ProductField.SIZES, item.substring(colonIndex+1).trim());

                } else if (ProductField.PRICE.toString().equals(prefix)) {
                    ret.put(ProductField.PRICE, item.substring(colonIndex+1).trim());

                } else if (ProductField.NEW_PRICE.toString().equals(prefix)) {
                    ret.put(ProductField.NEW_PRICE, item.substring(colonIndex+1).trim());

                } else if (ProductField.ACTION.toString().equals(prefix)) {
                    ret.put(ProductField.ACTION, item.substring(colonIndex+1).trim());

                } else if (ProductField.BRAND.toString().equals(prefix)) {
                    ret.put(ProductField.BRAND, item.substring(colonIndex+1).trim());

                }
            } else {
                ret.put(ProductField.NAME, item);
            }
        }
        return ret;
    }

    /**
     * Метод создает продукт по описанию
     * @param id - идентификатор продукта
     * @param category - внешний идентификатор категории продукта
     * @param productDescription - атрибуты продукта (название, цена, размеры и т.д.) в виде строки
     * @param smallPhotoURL - URL маленькой фоторгафии продукта
     * @param largePhotoURL - URL большой фоторгафии продукта
     * @return Продукт
     */
    public static Product createProductByParams(int id, String category, String productDescription,
                                                String smallPhotoURL, String largePhotoURL) {
        UrlValidator UrlValidator = new UrlValidator();
        if (!UrlValidator.isValid(smallPhotoURL)) {
            throw new IllegalArgumentException("smallPhotoURL must be a valid URL for productId = " + id);
        }
        if (!UrlValidator.isValid(largePhotoURL)) {
            throw new IllegalArgumentException("largePhotoURL must be a valid URL for productId = " + id);
        }
        if (productDescription == null || productDescription == "") {
            throw new IllegalArgumentException("productDescription must be set for productId = " + id);
        }
        HashMap<ProductField, String> productFields = getProductsParams(productDescription);
        if (!productFields.containsKey(ProductField.NAME))
            throw new IllegalArgumentException("Name must be defined in productDescription for productId = " + id);

        Product product = new Product();
        product.id = id;
        product.category = category;
        product.name = productFields.get(ProductField.NAME);
        product.sizes = productFields.get(ProductField.SIZES);

        try {
            product.price = Integer.parseInt(productFields.get(ProductField.PRICE));
            product.newPrice = product.price;
        } catch (NumberFormatException ex) {
            // ToDo log
            product.price = 0;
        }

        try {
            product.newPrice = Integer.parseInt(productFields.get(ProductField.NEW_PRICE));
        } catch (NumberFormatException ex) {
            // ToDo log
            product.newPrice = product.price;
        }

        product.actionDetails = productFields.get(ProductField.ACTION);
        product.actionEnabled = (product.actionDetails == null)? false : true;

        product.brand = productFields.get(ProductField.BRAND);


        product.smallPhotoURL = smallPhotoURL;
        product.largePhotoURL = largePhotoURL;
        return product;
    }

    public int getId() {
        return id;
    }

    public String getCategory() { return category; }

    public String getName() {
        return name;
    }

    public String getSizes() {
        return sizes;
    }

    public int getPrice() {
        return price;
    }

    public int getNewPrice() {
        return newPrice;
    }

    public boolean isActionEnabled() {
        return actionEnabled;
    }

    public String getActionDetails() { return actionDetails; }

    public String getBrand() { return brand; }

    public String getSmallPhotoURL() {
        return smallPhotoURL;
    }

    public String getLargePhotoURL() {
        return largePhotoURL;
    }

    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("Product");
        sBuilder.append("\nid: ");
        sBuilder.append(id);
        sBuilder.append("\nname: ");
        sBuilder.append(name);
        sBuilder.append("\ncategory: ");
        sBuilder.append(category);
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
        sBuilder.append(brand);
        sBuilder.append("\nsmallPhotoURL: ");
        sBuilder.append(smallPhotoURL);
        sBuilder.append("\nlargePhotoURL: ");
        sBuilder.append(largePhotoURL);
        return sBuilder.toString();
    }

}