package com.sap.cc.ascii;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sap.cc.InvalidRequestException;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;

import okhttp3.mockwebserver.MockWebServer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class AsciiArtServiceClientTest {

    public static final AsciiArtRequest WITH_VALID_ARGS = new AsciiArtRequest("HelloWorld", "3");
    public static final AsciiArtRequest WITH_UNKNOWN_FONT_ID = new AsciiArtRequest("handleThis", "9");

    public static final HttpClientErrorException BAD_REQUEST_EXCEPTION = HttpClientErrorException
            .create(HttpStatus.BAD_REQUEST, "Font not found for fontId 9. Try a fontId within 0..8", null, null, null);

    private BaseUrlProvider baseUrlProvider = Mockito.mock(BaseUrlProvider.class);
    private AsciiArtServiceClient asciiArtServiceClient;
    public static MockWebServer mockBackEnd;
    private ObjectMapper objectMapper = new ObjectMapper();
    private AsciiArtResponse asciiArtResponse = new AsciiArtResponse("'Hello World' as ascii art", "comic");

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    void initialize() {
        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        Mockito.when(baseUrlProvider.getBaseUrl()).thenReturn(baseUrl);
        asciiArtServiceClient = new AsciiArtServiceClient(WebClient.create(), baseUrlProvider);
    }

    @Test
    public void whenCallingGetAsciiString_thenClientMakesCorrectCallToService() throws JsonProcessingException {
        String beautifiedString = objectMapper.writeValueAsString(asciiArtResponse);
        mockBackEnd.enqueue(new MockResponse().setBody(beautifiedString)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));
        assertTrue(asciiArtServiceClient.getAsciiString(WITH_VALID_ARGS).equals("'Hello World' as ascii art"));
    }

    @Test
    public void whenRequestingWithInvalidRequest_thenInvalidRequestExceptionIsThrown(){
        mockBackEnd.enqueue(new MockResponse().setResponseCode(HttpStatus.BAD_REQUEST.value()));
        assertThrows(InvalidRequestException.class, () -> asciiArtServiceClient.getAsciiString(WITH_UNKNOWN_FONT_ID));
    }

}