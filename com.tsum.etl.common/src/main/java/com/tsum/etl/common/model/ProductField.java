package com.tsum.etl.common.model;


/**
 * Перечисление префиксов полей, описывающих продукт
 */
public final class ProductField {
    private final String val;
    private ProductField(String val) { this.val = val; }

    public String toString() { return val; }

    public static final ProductField NAME = new ProductField("НАЗВАНИЕ");
    public static final ProductField SIZES = new ProductField("РАЗМЕРЫ");
    public static final ProductField PRICE = new ProductField("ЦЕНА");
    public static final ProductField NEW_PRICE = new ProductField("НОВАЯ ЦЕНА");
    public static final ProductField ACTION = new ProductField("АКЦИЯ");
    public static final ProductField BRAND = new ProductField("БРЕНД");
}
