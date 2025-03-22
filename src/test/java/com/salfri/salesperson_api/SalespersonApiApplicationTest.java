package com.salfri.salesperson_api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SalespersonApiApplicationTest {
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
    }
    @Test
    public void shouldCreateAndTestSalesPerson() throws Exception {

        RestAssured.baseURI = "http://localhost:8080/sales";

        // Step 1: Create a new Salesperson via POST
        String requestBody = "{\"name\":\"Abi\", " +
                "\"location\":\"CHENNAI\", " +
                "\"role\":\"SOFTWARE_QA_ENGINEER\",\"email\": \"updatedemail@example.com\",\"mobileNumber\":\"9876543210\"}";

        Response postResponse = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("")
                .then()
                .statusCode(201) // Assert HTTP status for successful creation
                .extract().response();

        // Step 2: Extract ID from POST response
        String postResponseBody = postResponse.getBody().asString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode postJsonNode = objectMapper.readTree(postResponseBody);
        Integer createdId = postJsonNode.get("id").asInt();

        // Step 3: Send GET request to validate the created Salesperson
        Response getResponse = given()
                .when()
                .get("/" + createdId)
                .then()
                .statusCode(200) // Assert HTTP status for successful retrieval
                .extract().response();

        // Step 4: Extract and assert values from GET response
        String getResponseBody = getResponse.getBody().asString();
        JsonNode getJsonNode = objectMapper.readTree(getResponseBody);

        Integer id = getJsonNode.get("id").asInt();
        String name = getJsonNode.get("name").asText();
        String location = getJsonNode.get("location").asText();
        String role = getJsonNode.get("role").asText();
        String email = getJsonNode.get("email").asText();
        String mobileNumber = getJsonNode.get("9876543210").asText();

        assertEquals(createdId, id);
        assertEquals("Abi", name);
        assertEquals("CHENNAI", location);
        assertEquals("SOFTWARE_QA_ENGINEER", role);
        assertEquals("updatedemail@example.com",email);
        assertEquals("9876543210",mobileNumber);


        // Step 3: Send DELETE request to remove the created Salesperson
        given()
                .when()
                .delete("/" + createdId)
                .then()
                .statusCode(204); // 204 No Content (successful deletion)

        // Step 4: Try to GET the deleted Salesperson (should return 404)
        given()
                .when()
                .get("/" + createdId)
                .then()
                .statusCode(404); // Not Found (confirm deletion)
    }
}

