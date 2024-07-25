package com.neptune.boards.tests.e2e;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProjectCreationTest {

    @Test
    @DisplayName("E2E: Create Project: Create a simple project with three basic states (To do, doing and done)")
    @Order(1)
    public void createProjectTest() {
        String requestBody = """
            {
              "name": "Web Redesign",
              "description": "Project to redesign the company website."
              "allowedTaskStates": [
                  {
                    "name":"To Do",
                    "definitionOfDone":"Tasks that need to be started"
                    "stateUUID": "7e8b9e4a-1d1b-4f30-9a4e-c6f8e7e8f2a6"
                    "default":"True"
                  },
                  {
                    "name":"Doing",
                    "definitionOfDone":"Tasks currently in progress."
                    "stateUUID": "8c1e6d7a-3f2c-4f40-b5a4-d6e7f9f8e1b2"
                    "default":"False"
                  },
                  {
                    "name":"Done",
                    "definitionOfDone":"Tasks that have been completed."
                    "stateUUID": "9a3d7f6b-4c5d-4e60-c6a4-e7f9f1a2b3c4"
                    "default":"False"
                  },
              ]
            }
         """;
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/project/" + UUID.randomUUID())
                .then()
                .statusCode(200)
                .body("name", equalTo("Web Redesign"))
                .body("description", equalTo("Project to redesign the company website."));
    }
}
