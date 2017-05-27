package com.tsum.etl.vkreader;


import com.tsum.etl.common.ProductReader;
import com.tsum.etl.common.model.Product;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.ServiceClientCredentialsFlowResponse;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.photos.responses.GetResponse;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Класс, считывающий информацию о продуктам ЦУМа из фото-альбомов VK
 * Реализует интерфейс {@link ProductReader}
 * Created by aam on 08.03.17.
 */
@Service
public class VKProductReader implements ProductReader, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(ProductReader.class);
    @Value("${vkreader.client-id}")
    private int clientId;
    @Value("${vkreader.app-id}")
    private int appId;
    @Value("${vkreader.client-secret}")
    private String clientSecret;
    @Value("${vkreader.owner-id}")
    private int ownerId;

    VkApiClient vk;

    public void afterPropertiesSet() throws ApiException, ClientException {
        TransportClient transportClient = HttpTransportClient.getInstance();
        vk = new VkApiClient(transportClient);
        ServiceClientCredentialsFlowResponse authResponse = vk.oauth().serviceClientCredentialsFlow(
                clientId, clientSecret).execute();
    }

    public Collection<Product> getProducts(Collection<String> productId) {
        throw new UnsupportedOperationException();
    }

    public Collection<Product> getProductsByCategory(String categoryId) throws Exception {
        ArrayList<Product> products = new ArrayList<Product>();
        int stepSize = 10;
        int startIndex = 0;

        while (true) {
            GetResponse response = vk.photos().get()
                    .ownerId(ownerId)
                    .albumId(categoryId)
                    .count(stepSize)
                    .offset(startIndex)
                    .execute();

            List<Photo> photos = response.getItems();

            if (photos.size() == 0)
                break;

            startIndex += photos.size();
            for (Photo photo : photos) {
                try {
                    String smallPhotoURL;
                    if (photo.getPhoto604() != null) {
                        smallPhotoURL = photo.getPhoto604();
                    } else if (photo.getPhoto130() != null) {
                        smallPhotoURL = photo.getPhoto130();
                    } else {
                        smallPhotoURL = photo.getPhoto75();
                    }

                    String largerPhotoURL;
                    if (photo.getPhoto2560() != null) {
                        largerPhotoURL = photo.getPhoto2560();
                    } else if (photo.getPhoto1280() != null) {
                        largerPhotoURL = photo.getPhoto1280();
                    } else if (photo.getPhoto807() != null) {
                        largerPhotoURL = photo.getPhoto807();
                    } else {
                        largerPhotoURL = smallPhotoURL;
                    }

                    Product product = Product.createProductByParams(photo.getId(),
                            categoryId,
                            photo.getText(),
                            smallPhotoURL,
                            largerPhotoURL
                    );
                    products.add(product);
                } catch (IllegalArgumentException ex) {
                    logger.warn("Product id={} was not loaded. Reason: {}", photo.getId(), ex.getMessage());
                }
            }
        }
        return products;
    }
}
