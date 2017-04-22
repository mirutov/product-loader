package com.tsum.etl.common.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by aam on 19.03.17.
 */
public class ProductTest {
    @Test
    public void createProductByParams() throws Exception {
        try {
            Product.createProductByParams(1, null, null, null, null);
        } catch (IllegalArgumentException ex) {
            assertEquals(ex.getMessage(), "smallPhotoURL must be a valid URL for productId = 1");
        }

        try {
            Product.createProductByParams(1, null, null, "http://rbc.ru", null);
        } catch (IllegalArgumentException ex) {
            assertEquals(ex.getMessage(), "largePhotoURL must be a valid URL for productId = 1");
        }

        try {
            Product.createProductByParams(1, null, null, "http://lenta.ru", "http://rbc.ru");
        } catch (IllegalArgumentException ex) {
            assertEquals("productDescription must be set for productId = 1", ex.getMessage());
        }

        try {
            Product.createProductByParams(1, null, "", "http://rbc.ru", "http://rbc.ru");
        } catch (IllegalArgumentException ex) {
            assertEquals(ex.getMessage(), "productDescription must be set for productId = 1");
        }
    }

}