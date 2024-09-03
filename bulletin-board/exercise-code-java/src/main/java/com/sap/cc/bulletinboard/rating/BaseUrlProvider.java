package com.sap.cc.bulletinboard.rating;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BaseUrlProvider {
    @Value("${service.rating.url.base}")
    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }
}
