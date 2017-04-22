package com.tsum.etl.manager;

import com.tsum.etl.common.ETLManager;
import com.tsum.etl.common.ProductReader;
import com.tsum.etl.common.ProductWriter;
import com.tsum.etl.common.model.Product;
import com.tsum.etl.tsumwriter.service.ProductService;
import com.tsum.etl.manager.utils.ProductSource;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by aam on 14.04.17.
 */
public class TsumEtlManagerTest {
    private static final Logger logger = LoggerFactory.getLogger(TsumEtlManagerTest.class);
    private static  String beginningOfTime = "";
    private ETLManager etlManager;
    private ProductWriter writer;
    private ProductService productService;
    private ProductSource productSrc;
    private Collection<String> categoriesExternalIds;

    @Before
    public void setUp() {

        if (etlManager != null)
            return;
        try {
            GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
            ctx.load("context-test.xml");
            ctx.refresh();
            etlManager = ctx.getBean(ETLManager.class);
            productService = ctx.getBean(ProductService.class);
            productSrc = (ProductSource)ctx.getBean(ProductReader.class);
            categoriesExternalIds = productSrc.getCategoryIds();
        } catch (Exception ex) {
            logger.error("Error: ", ex);
        }
    }

    @Test
    public void etlByCategories() throws Exception {
        etlManager.etlByCategories(categoriesExternalIds);
        checkETLResults();
    }

    private void checkETLResults() throws Exception {
        Collection<com.tsum.etl.tsumwriter.entity.Product> dbProducts = productService.findAll();
        if (dbProducts.size() != 8)
            throw new Exception("Number of products is invalid");

        int unchangedProducts = 0;
        for (com.tsum.etl.tsumwriter.entity.Product dbProduct : dbProducts) {
            if (dbProduct.getUpdateTimestamp().equals(Timestamp.valueOf("2017-01-01 00:00:000.000")))
                unchangedProducts++;
        }

        if (unchangedProducts != 2)
            throw new Exception("Number of unchanged products is invalid");

        Collection<Product> products = productSrc.getAllProducts();

        for (Product product : products) {
            com.tsum.etl.tsumwriter.entity.Product dbProduct = productService.findById(product.getId());
            assertProductEquals(product, dbProduct);
        }
    }

    private void assertProductEquals(Product etlProduct,
                                     com.tsum.etl.tsumwriter.entity.Product dbProduct) throws Exception
    {
        if (!etlProduct.getName().equals(dbProduct.getName()))
            throw new Exception("names are not equivalent for products " + etlProduct.getId());


        if (!strEquals(etlProduct.getSizes(),dbProduct.getSizes()))
            throw new Exception("sizes are not equivalent for products " + etlProduct.getId());

        if (etlProduct.getPrice() != dbProduct.getPrice())
            throw new Exception("prices are not equivalent for products " + etlProduct.getId());

        if (etlProduct.getNewPrice() != dbProduct.getNewPrice())
            throw new Exception("newPrices are not equivalent for products " + etlProduct.getId());

        if (etlProduct.isActionEnabled() != dbProduct.isActionEnabled())
            throw new Exception("isActionEnabled flags are not equivalent for products " + etlProduct.getId());

        if (!strEquals(etlProduct.getActionDetails(), dbProduct.getActionDetails()))
            throw new Exception("Actions details are not equivalent for products " + etlProduct.getId());

        if (!strEquals(etlProduct.getLargePhotoURL(), dbProduct.getLargePhotoURL()))
            throw new Exception("Large photo URLs are not equivalent for product s" + etlProduct.getId());

        if (!strEquals(etlProduct.getSmallPhotoURL(), dbProduct.getSmallPhotoURL()))
            throw new Exception("Small photo URLs are not equivalent for product s" + etlProduct.getId());

        if (!strEquals(etlProduct.getCategory(), dbProduct.getCategory().getExternalId()))
            throw new Exception("Categories are not equivalent for product s" + etlProduct.getId());

        if (!strEquals(etlProduct.getBrand(), dbProduct.getBrand().getName()))
            throw new Exception("Brands photo URLs are not equivalent for product s" + etlProduct.getId());
    }

    private boolean strEquals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }
 }