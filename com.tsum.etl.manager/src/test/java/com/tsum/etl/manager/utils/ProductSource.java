package com.tsum.etl.manager.utils;

import com.tsum.etl.common.ProductReader;
import com.tsum.etl.common.model.Product;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by aam on 11.04.17.
 */
@Service
public class ProductSource implements ProductReader, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(ProductSource.class);
    private static final String detaultURL = "http://default.ru";
    private ConcurrentHashMap<String, Collection<Product>> productsByCategories;

    public void afterPropertiesSet() {
        productsByCategories = new ConcurrentHashMap<String, Collection<Product>>();

        try {
            JSONParser parser = new JSONParser();
            Resource resource = new ClassPathResource("products.json");
            InputStream resourceInputStream = resource.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(resourceInputStream));
            String productsAsString = br.lines().parallel().collect(Collectors.joining("\n"));

            JSONArray categoriesJSON = (JSONArray) parser.parse(productsAsString);
            for (int i = 0; i < categoriesJSON.size(); i++) {
                JSONObject categoryJSON = (JSONObject)categoriesJSON.get(i);
                String externalCategoryId = (String)categoryJSON.get("categoryId");
                JSONArray productsJSON = (JSONArray)categoryJSON.get("products");
                List<Product> products = new ArrayList<Product>();
                for (int j = 0; j < productsJSON.size(); j++ ) {
                    JSONObject productJSON = (JSONObject)productsJSON.get(j);
                    int productId = new Integer(((String)productJSON.get("productId")));
                    try {
                        String description = ((String) productJSON.get("productDescription")).replace(';', '\n');
                        Product product = Product.createProductByParams(productId, externalCategoryId, description,
                                detaultURL, detaultURL);
                        products.add(product);
                    } catch (IllegalArgumentException ex) {
                        logger.warn("Exception: " + ex);
                    }
                }
                productsByCategories.put(externalCategoryId, products);
            }
        }
        catch (Exception ex) {
            logger.error("Exception: " + ex);
            productsByCategories.clear();
        }
    }

    public Collection<Product> getProducts(Collection<String> productId) {
        throw new UnsupportedOperationException();
    }

    public Collection<Product> getProductsByCategory(String categoryId) throws Exception {
        return new LinkedList<Product>(productsByCategories.get(categoryId));
    }

    public Collection<String> getCategoryIds() {
        return new LinkedList<String>(Collections.list(productsByCategories.keys()));
    }

    public Collection<Product> getAllProducts() {
        LinkedList<Product> products = new LinkedList<Product>();
        Iterator<String> keysIterator = productsByCategories.keySet().iterator();
        while (keysIterator.hasNext()) {
            String key = keysIterator.next();
            products.addAll(new LinkedList<Product>(productsByCategories.get(key)));
        }
        return products;
    }

}
