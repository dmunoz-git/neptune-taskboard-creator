package com.neptune.boards.tests.e2e.tests;

import com.neptune.boards.tests.e2e.config.Config;
import com.neptune.boards.tests.e2e.utils.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;

import java.io.IOException;
import java.util.UUID;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ProjectE2ETest {
    private static final UUID projectId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = Config.BASE_URL;
    }

    @Test
    @DisplayName("E2E Test: Create a new Project")
    void createProject() throws IOException {
        String projectJson = JsonUtils.readJsonFile("src/test/java/com/neptune/boards/tests/e2e/models/project/project.json");

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
}
