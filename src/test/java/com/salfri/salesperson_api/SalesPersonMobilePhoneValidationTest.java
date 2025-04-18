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

public class SalesPersonMobilePhoneValidationTest {

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
    void shouldReturnBadRequestForInvalidNullMobileNumber() throws Exception {
        String invalidRequestBodyWithNullRole = """
            {   "name" : "Abirami",
                 "location":"Germany",
                "role":"General Manager",
                "email" :"abi@gmail.com",
                
                "totalSalesCount": 10,
                "joiningDate": "2025-01-01",
                "status": "ACTIVE",
                "totalRevenue": 1000.00,
                "departmentName": "Sales",
                "designation": "Executive",
                "performanceRating": 3,
                "gender": "MALE",
                "address": "Sample Address",
                "photoUrl": "https://scbugit.com/images/test.png"
            }
            """;

        Response response = given()
                .header("Content-Type", "application/json")
                .body(invalidRequestBodyWithNullRole)
                .when()
                .post()
                .then()
                .extract()
                .response();

        assertEquals(400, response.getStatusCode(), "Expected HTTP 400 Bad Request");

        String responseBody = response.getBody().asString();
        JsonNode json = objectMapper.readTree(responseBody);

        String actualMessage = json.get("mobileNumber").asText();
        assertEquals("Mobile number cannot be null or empty", actualMessage);
    }
    @ParameterizedTest(name = "[{index}] name=\"{0}\" → expect: \"{1}\"")
    @MethodSource("provideInvalidMobileNumber")
    void shouldReturnBadRequestForInvalidRole(String mobileNumber, String expectedMessage) throws Exception {

        String invalidRequestBody = String.format("""
            {
                "name": "Abi",
                "location": "Germany",
                "role": "General Manager",
                "email": "abi@gmail.com",
                "mobileNumber": "%s",
                "totalSalesCount": 10,
                "joiningDate": "2025-01-01",
                "status": "ACTIVE",
                "totalRevenue": 1000.00,
                "departmentName": "Sales",
                "designation": "Executive",
                "performanceRating": 3,
                "gender": "MALE",
                "address": "Sample Address",
                "photoUrl": "https://scbugit.com/images/test.png"
            }
            """, mobileNumber);

        Response response = given()
                .header("Content-Type", "application/json")
                .body(invalidRequestBody)
                .when()
                .post()
                .then()
                .extract()
                .response();

        assertEquals(400, response.getStatusCode(), "Expected HTTP 400 Bad Request");

        String responseBody = response.getBody().asString();
        JsonNode json = objectMapper.readTree(responseBody);

        // Print for debugging
        System.out.printf("Input name='%s' → Received message='%s'%n", mobileNumber, json.get("mobileNumber").asText());

        String actualMessage = json.get("mobileNumber").asText();
        assertEquals(expectedMessage, actualMessage);
    }

    static Stream<Arguments> provideInvalidMobileNumber() {
        return Stream.of(

                Arguments.of("!23456", "Mobile number must contain only digits and can optionally " +
                        "start with a '+'; length must be between 10 and 15 digits"),
                Arguments.of("qwertjk", "Mobile number must contain only digits and can optionally " +
                        "start with a '+'; length must be between 10 and 15 digits"),
                Arguments.of("=1234567890", "Mobile number must contain only digits and can optionally " +
                        "start with a '+'; length must be between 10 and 15 digits")

        );
    }
}
