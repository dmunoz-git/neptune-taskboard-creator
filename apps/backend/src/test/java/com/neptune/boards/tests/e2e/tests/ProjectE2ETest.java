package com.neptune.boards.tests.e2e.tests;

import com.neptune.boards.tests.e2e.config.Config;
import com.neptune.boards.tests.e2e.utils.JsonUtils;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import io.restassured.RestAssured;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.io.IOException;
import java.util.UUID;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@Disabled
public class ProjectE2ETest {
    private static final UUID projectId = UUID.fromString("90b18fbf-89ea-4c7a-8490-8f9e127f4261");

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = Config.BASE_URL;
    }

    @Test
    @Order(1)
    @DisplayName("E2E Test: Create a new Project")
    void createProject() throws IOException {
        String projectJson = JsonUtils.readJsonFile("src/test/java/com/neptune/boards/tests/e2e/models/project.json");

        // Create project
        given()
                .contentType("application/json")
                .body(projectJson)
                .when()
                .post("/api/project/" + projectId)
                .then()
                .statusCode(201)
                .body("name", equalTo("Test Project"))
                .body("description", equalTo("A project created for E2E testing purposes"));
    }

    @Test
    @Order(2)
    @DisplayName("E2E Test: Update Project description")
    void updateProject() throws IOException {
        // Update project description
        String updateJson = "{ \"description\": \"A project created for E2E testing purposes has been updated\" }";

        // Perform the PUT request and capture the response
        Response response = given()
                .contentType("application/json")
                .body(updateJson)
                .when()
                .put("/api/project/" + projectId);

        // Print the response for debugging purposes
        response.prettyPrint();

        // Verify the response status and body
        response.then()
                .statusCode(200)
                .body("name", equalTo("Test Project"))
                .body("description", equalTo("A project created for E2E testing purposes has been updated"));
    }

    @Test
    @Order(3)
    @DisplayName("E2E Test: Get Project by UUID")
    void getProjectByUUID() {
        RestAssured.baseURI = "http://localhost:8080";

        given()
                .pathParam("uuid", "90b18fbf-89ea-4c7a-8490-8f9e127f4261")
                .when()
                .get("/api/project/{uuid}")
                .then()
                .statusCode(200)
                .body("uuid", equalTo("90b18fbf-89ea-4c7a-8490-8f9e127f4261"))
                .body("name", equalTo("Test Project"))
                .body("description", equalTo("A project created for E2E testing purposes has been updated"));
    }

    @Test
    @Order(4)
    @DisplayName("E2E Test: List All Projects")
    void listAllProjects() {
        RestAssured.baseURI = "http://localhost:8080";

        given()
                .when()
                .get("/api/project/list")
                .then()
                .statusCode(200)
                .body("$", hasSize(1));
    }

    @Test
    @Order(5)
    @DisplayName("E2E Test: Remove Project by UUID")
    void removeProjectByUUID() {
        RestAssured.baseURI = "http://localhost:8080";

        given()
                .pathParam("uuid", "90b18fbf-89ea-4c7a-8490-8f9e127f4261")
                .when()
                .delete("/api/project/{uuid}")
                .then()
                .statusCode(200)
                .body("uuid", equalTo("90b18fbf-89ea-4c7a-8490-8f9e127f4261"))
                .body("name", equalTo("Test Project"));
    }
}
