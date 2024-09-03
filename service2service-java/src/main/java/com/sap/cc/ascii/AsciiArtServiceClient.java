package com.sap.cc.ascii;

import com.sap.cc.InvalidRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class AsciiArtServiceClient {

    private static final String ASCII_SERVICE_PATH = "/api/v2/asciiArt/";

    @Value("${service.ascii.username}")
    private String asciiServiceUsername = "";

    @Value("${service.ascii.password}")
    private String asciiServicePassword = "";

    private final WebClient webClient;
    private final BaseUrlProvider baseUrlProvider;

    public AsciiArtServiceClient(WebClient webClient, BaseUrlProvider baseUrlProvider) {
        this.webClient = webClient;
        this.baseUrlProvider = baseUrlProvider;
    }

    public String getAsciiString(AsciiArtRequest asciiArtRequest) {
        try {
            return webClient.post()
                            .uri(baseUrlProvider.getBaseUrl() + ASCII_SERVICE_PATH)
                            .headers(httpHeaders -> httpHeaders.setBasicAuth(asciiServiceUsername, asciiServicePassword))
                            .bodyValue(asciiArtRequest)
                            .retrieve()
                            .bodyToMono(AsciiArtResponse.class)
                            .block()
                            .getBeautifiedText();
        }catch (WebClientResponseException.BadRequest badRequest){
            throw new InvalidRequestException("Invalid request", badRequest);
        }
    }
}