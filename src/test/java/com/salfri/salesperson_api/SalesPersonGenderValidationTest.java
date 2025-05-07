package com.salfri.salesperson_api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SalesPersonGenderValidationTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void waitForServer() {
        await()
                .atMost(60, SECONDS)
                .pollInterval(1, SECONDS)
                .ignoreExceptions()
                .until(() -> {
                    int statusCode = given()
                            .when()
                            .get("http://localhost:8080/actuator/health")
                            .then()
                            .extract()
                            .statusCode();
                    return statusCode == HttpStatus.OK.value();
                });

        RestAssured.baseURI = "http://localhost:8080/sales";
    }
    @Test
    void shouldReturnBadRequestForInvalidGenderEnum() throws Exception {
        String invalidRequestBodyWithNullGender = """
            {   "name" : "Abirami",
                 "location":"Germany",
                 "role" : "QA test Engineer",
                "email" :"abi@gmail.com",
                "mobileNumber": "1234567890",
                "totalSalesCount": 10,
                "joiningDate": "2025-01-01",
                "status": "ACTIVE",
                "totalRevenue": 1000.00,
                "departmentName": "Sales",
                "designation": "Executive",
                "performanceRating": 3,
                
                "address": "Sample Address",
                "photoUrl": "https://scbugit.com/images/test.png"
            }
            """;

        Response response = given()
                .header("Content-Type", "application/json")
                .body(invalidRequestBodyWithNullGender)
                .when()
                .post()
                .then()
                .extract()
                .response();

        assertEquals(400, response.getStatusCode(), "Expected HTTP 400 Bad Request");

        String responseBody = response.getBody().asString();
        JsonNode json = objectMapper.readTree(responseBody);

        String actualMessage = json.get("gender").asText();
        assertEquals("Gender cannot be null", actualMessage);
    }
}
