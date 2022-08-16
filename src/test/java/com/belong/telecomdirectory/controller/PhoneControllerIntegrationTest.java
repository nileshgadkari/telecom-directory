package com.belong.telecomdirectory.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"integrationtest"})
@Sql(
        executionPhase = BEFORE_TEST_METHOD,
        scripts = {
                "classpath:scripts/create_tables.sql",
                "classpath:scripts/fill_tables.sql"
        }
)
public class PhoneControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testGetAllPhoneNumbers() {
        var response = testRestTemplate.exchange(
                createURLWithPort("/customer/phone-numbers"),
                HttpMethod.GET,
                new HttpEntity<>(null, new HttpHeaders()),
                String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertTrue(response.getBody().contains("Tom"));
        Assertions.assertTrue(response.getBody().contains("Elon"));
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
