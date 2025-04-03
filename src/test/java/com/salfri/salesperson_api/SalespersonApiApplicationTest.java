package com.salfri.salesperson_api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    public void shouldVerifyCreateUpdateGetApi() throws Exception {

        RestAssured.baseURI = "http://localhost:8080/sales";

        // Step 1: Create a new Salesperson via POST
        String requestBody = "{\"name\":\"Abi\", " +
                "\"location\":\"CHENNAI\", " +
                "\"role\":\"SOFTWARE_QA_ENGINEER\",\"email\": \"updatedemail@example.com\",\"mobileNumber\":\"9876543210\",\"totalSalesCount\":100,\"joiningDate\":\"2025-01-12\"," +
                "\"status\":\"ACTIVE\",\"totalRevenue\": 100000.75 ,\"departmentName\": \"General\",\"designation\": \"Sales Executive\",\"performanceRating\":4}";

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
        String mobileNumber = getJsonNode.get("mobileNumber").asText();
        int totalSalesCount = getJsonNode.get("totalSalesCount").asInt();
        String joiningDate = getJsonNode.get("joiningDate").asText();
        String status = getJsonNode.get("status").asText();
        BigDecimal totalRevenue = BigDecimal.valueOf(getJsonNode.get("totalRevenue").asDouble());
        String departmentName = getJsonNode.get("departmentName").asText();
        String designation = getJsonNode.get("designation").asText();
        Integer performanceRating = getJsonNode.get("performanceRating").asInt();


        assertEquals(createdId, id);
        assertEquals("Abi", name);
        assertEquals("CHENNAI", location);
        assertEquals("SOFTWARE_QA_ENGINEER", role);
        assertEquals("updatedemail@example.com",email);
        assertEquals("9876543210",mobileNumber);
        assertEquals(100,totalSalesCount);
        assertEquals("2025-01-12",joiningDate);
        assertEquals("ACTIVE",status);
        assertEquals(BigDecimal.valueOf(100000.75), totalRevenue);
        assertEquals("General",departmentName);
        assertEquals("Sales Executive",designation);
        assertEquals(4,performanceRating);

        // Step 5: Send PUT request to update Salesperson details
        String updatedRequestBody = "{\"name\":\"Kamachi Updated\", " +
                "\"location\":\"Germany\", " +
                "\"role\":\"Senior Manager\",\"email\": \"kanch_updated@gmail.com\",\"mobileNumber\":\"987654321\"," +
                "\"totalSalesCount\":150,\"joiningDate\":\"1985-05-15\"," +
                "\"status\":\"INACTIVE\",\"totalRevenue\": 2000000.99 ," +
                "\"departmentName\": \"Sales\",\"designation\": \"Team Lead\",\"performanceRating\":5}";

        given()
                .header("Content-Type", "application/json")
                .body(updatedRequestBody)
                .when()
                .put("/" + createdId)
                .then()
                .statusCode(200); // Assert HTTP status for successful update

        // Step 6: Retrieve and validate the updated Salesperson details
        Response updatedGetResponse = given()
                .when()
                .get("/" + createdId)
                .then()
                .statusCode(200)
                .extract().response();

        // Extract updated values
        String updatedResponseBody = updatedGetResponse.getBody().asString();
        JsonNode updatedJsonNode = objectMapper.readTree(updatedResponseBody);

        assertEquals(createdId, updatedJsonNode.get("id").asInt());
        assertEquals("Kamachi Updated", updatedJsonNode.get("name").asText());
        assertEquals("Germany", updatedJsonNode.get("location").asText());
        assertEquals("Senior Manager", updatedJsonNode.get("role").asText());
        assertEquals("kanch_updated@gmail.com", updatedJsonNode.get("email").asText());
        assertEquals("987654321", updatedJsonNode.get("mobileNumber").asText());
        assertEquals(150, updatedJsonNode.get("totalSalesCount").asInt());
        assertEquals("1985-05-15", updatedJsonNode.get("joiningDate").asText());
        assertEquals("INACTIVE", updatedJsonNode.get("status").asText());
        assertEquals(BigDecimal.valueOf(2000000.99), BigDecimal.valueOf(updatedJsonNode.get("totalRevenue").asDouble()));
        assertEquals("Sales", updatedJsonNode.get("departmentName").asText());
        assertEquals("Team Lead", updatedJsonNode.get("designation").asText());
        assertEquals(5, updatedJsonNode.get("performanceRating").asInt());



        // Step 7: Send DELETE request to remove the created Salesperson
        given()
                .when()
                .delete("/" + createdId)
                .then()
                .statusCode(204); // 204 No Content (successful deletion)

        // Step 8: Try to GET the deleted Salesperson (should return 404)
        given()
                .when()
                .get("/" + createdId)
                .then()
                .statusCode(404); // Not Found (confirm deletion)
    }
}

