package com.elevenst.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class ProductRemoteServiceImpl implements ProductRemoteService {

    private static final String url = "http://localhost:8082/products/";
    private final RestTemplate restTemplate;

    public ProductRemoteServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @HystrixCommand(commandKey="productInfo", fallbackMethod="getProductInfoFallback")
    public String getProductInfo(String productId) {
        return this.restTemplate.getForObject(url + productId, String.class);
    }
    
    public String getProductInfoFallback(String productId, Throwable t) {
    	System.out.println("t = "+t);
    	return "[This Product is sold out]";
    }
}
