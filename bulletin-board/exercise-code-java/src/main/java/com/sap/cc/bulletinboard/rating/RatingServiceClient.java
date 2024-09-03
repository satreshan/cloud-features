package com.sap.cc.bulletinboard.rating;

import com.sap.cc.bulletinboard.InvalidRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class RatingServiceClient {

    private static final String RATING_SERVICE_PATH = "/api/v1/averageRatings/";

    @Value("${service.rating.username}")
    private String ratingServiceUsername = "";

    @Value("${service.rating.password}")
    private String ratingServicePassword = "";

    private WebClient webClient;
    private BaseUrlProvider baseUrlProvider;

    public RatingServiceClient(WebClient webClient, BaseUrlProvider baseUrlProvider) {
        this.webClient = webClient;
        this.baseUrlProvider = baseUrlProvider;
    }

    public Float getAverageRating(String email) {
        try {
            RatingResponse abc = webClient.get().uri(baseUrlProvider.getBaseUrl()+RATING_SERVICE_PATH+ email)
                    .headers(httpHeaders -> httpHeaders.setBasicAuth(ratingServiceUsername, ratingServicePassword))
                    .retrieve()
                    .bodyToMono(RatingResponse.class)
                    .block();
            assert abc != null;
            Float ratings = abc.getAverage_rating();
            return  ratings == null ? 0.0f : abc.getAverage_rating();
        }
        catch(WebClientResponseException.BadRequest badRequest){
            throw new InvalidRequestException("Invalid request",badRequest);
        }
    }
}