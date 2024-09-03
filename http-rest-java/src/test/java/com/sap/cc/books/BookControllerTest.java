package com.sap.cc.books;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void cleanup() throws Exception {
        mockMvc.perform(delete("/api/v1/books"));
    }

    @Test
    public void getAll_noBooks_returnsEmptyList() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/books")).andReturn().getResponse();
        assertTrue(response.getStatus() == 200);
        assertTrue(response.getContentAsString().equals("[]"));
    }

    @Test
    public void addBook_returnsCreatedBook() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MockHttpServletResponse response = createBook();
        JsonNode responseJson = mapper.readTree(response.getContentAsString());
        assertTrue(response.getStatus() == 201);
        assertTrue(Objects.nonNull(responseJson.get("id")));
        assertTrue(responseJson.at("/author").asText().equals("P K DEV"));
    }

    @Test
    public void addBookAndGetSingle_returnsBook() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MockHttpServletResponse response = createBook();
        String location = response.getHeader("location");
        MockHttpServletResponse responseBook = mockMvc.perform(get(new URI(location))).andReturn().getResponse();
        assertTrue(responseBook.getStatus() == 200);
        assertTrue(mapper.readTree(responseBook.getContentAsString()).get("author").asText().equals("P K DEV"));
    }

    @Test
    public void getSingle_noBooks_returnsNotFound() throws Exception {
        MockHttpServletResponse responseBook =
                mockMvc.perform(get(new URI("/api/v1/books/2"))).andReturn().getResponse();
        assertTrue(responseBook.getStatus() == 404);
    }

    @Test
    public void addMultipleAndGetAll_returnsAddedBooks() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        createBook();
        MockHttpServletResponse responseGetAll = mockMvc.perform(get("/api/v1/books")).andReturn().getResponse();
        JsonNode getAllResponseJson = mapper.readTree(responseGetAll.getContentAsString());
        assertTrue(getAllResponseJson.size() == 1);
        assertTrue(responseGetAll.getStatus() == 200);

        createBook();
        responseGetAll = mockMvc.perform(get("/api/v1/books")).andReturn().getResponse();
        getAllResponseJson = mapper.readTree(responseGetAll.getContentAsString());
        assertTrue(getAllResponseJson.size() == 2);
        assertTrue(responseGetAll.getStatus() == 200);
    }

    @Test
    public void getSingle_idLessThanOne_returnsBadRequest() throws Exception {
        MockHttpServletResponse responseGetAll = mockMvc.perform(get("/api/v1/books/0")).andReturn().getResponse();
        assertTrue(responseGetAll.getStatus() == 400);
    }

    private MockHttpServletResponse createBook() throws Exception {
        String body = "{\n" + "    \"title\": \"cleanCode\",\n" + "    \"author\": \"P K DEV\"\n" + "}";
        return mockMvc.perform(post("/api/v1/books").content(body).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
    }

}