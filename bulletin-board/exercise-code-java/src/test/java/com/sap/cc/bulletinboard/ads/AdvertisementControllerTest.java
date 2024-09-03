package com.sap.cc.bulletinboard.ads;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.cc.bulletinboard.rating.RatingServiceClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdvertisementControllerTest {
/*
    @MockBean

    @MockBean
    RatingServiceClient ratingServiceClient;*/

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void cleanup() throws Exception {
        mockMvc.perform(delete("/api/v1/ads"));
    }
    @Test
    public void getRequestToEmpty() throws Exception {
        this.mockMvc.perform(get("/api/v1/ads"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[]")));
    }

    @Test
    public void getById() throws Exception {
        this.mockMvc.perform(get("/api/v1/ads/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("")));
    }

    @Test
    public void getByCreatedAd() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MockHttpServletResponse response = createAd();
        MockHttpServletResponse responseGet = mockMvc.perform(get(response.getHeader("location"))).andReturn().getResponse();
        JsonNode getAllResponseJson = mapper.readTree(responseGet.getContentAsString());
        assertTrue(getAllResponseJson != null);
        assertTrue(responseGet.getStatus() == 200);

    }

    private MockHttpServletResponse createAd() throws Exception {
        String body = "{\n" + "    \"title\": \"cleanCode\",\n" + "    \"contact\": \"1234567890\"\n" +  "}";
        return mockMvc.perform(post("/api/v1/ads").content(body).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
    }
}
