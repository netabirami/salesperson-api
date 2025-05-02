package com.salfri.salesperson_api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SalesPersonAddressValidationTest {

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
    void shouldReturnBadRequestForInvalidNullAddress() throws Exception {
        String invalidRequestBodyWithNullAddress = """
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
                    "gender": "MALE",
                    
                    "photoUrl": "https://scbugit.com/images/test.png"
                }
                """;

        Response response = given()
                .header("Content-Type", "application/json")
                .body(invalidRequestBodyWithNullAddress)
                .when()
                .post()
                .then()
                .extract()
                .response();

        assertEquals(400, response.getStatusCode(), "Expected HTTP 400 Bad Request");

        String responseBody = response.getBody().asString();
        JsonNode json = objectMapper.readTree(responseBody);

        String actualMessage = json.get("address").asText();
        assertEquals("Address must not be null.", actualMessage);
    }

    @Test
    void shouldReturnBadRequestForAddressLessThan10Characters() throws Exception {
        String invalidRequestBodyWithShortAddress = """
            {
                "name": "Abi",
                "location": "Germany",
                "role": "QA test Engineer",
                "email": "abi@gmail.com",
                "mobileNumber": "1234567890",
                "totalSalesCount": 10,
                "joiningDate": "2025-01-01",
                "status": "ACTIVE",
                "totalRevenue": 1000.00,
                "departmentName": "Sales",
                "designation": "Executive",
                "performanceRating": 3,
                "gender": "MALE",
                "address": "parkS21",
                "photoUrl": "https://scbugit.com/images/test.png"
            }
            """;

        Response response = given()
                .header("Content-Type", "application/json")
                .body(invalidRequestBodyWithShortAddress)
                .when()
                .post()
                .then()
                .extract()
                .response();

        assertEquals(400, response.getStatusCode(), "Expected HTTP 400 Bad Request");

        String responseBody = response.getBody().asString();
        JsonNode json = objectMapper.readTree(responseBody);

        String actualMessage = json.get("address").asText();
        assertEquals("Address must be between 10 and 255 characters.", actualMessage);
    }

}