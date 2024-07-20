package com.neptune.boards.tests.e2e;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

// TODO: Fix tests, create integration tests and e2e
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProjectE2ETest {

    @Test
    @DisplayName("E2E: Create Project")
    @Order(1)
    public void testCreateBoard(){
        String requestBody = """
            {
              "name": "Test Project",
              "description": "This is a test project"
            }
         """;
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/project/2f5d5fbc-7989-4e2b-8b8a-f2d9eaf58a6c")
                .then()
                .statusCode(200)
                .body("name", equalTo("Test Project"))
                .body("description", equalTo("This is a test project"));
    }

    @Test
    @DisplayName("E2E: Get Project by UUID")
    @Order(2)
    public void testGetBoardData(){
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/project/2f5d5fbc-7989-4e2b-8b8a-f2d9eaf58a6c")
                .then()
                .statusCode(200)
                .body("name", equalTo("Test Project"))
                .body("description", equalTo("This is a test project"));
    }

    @Test
    @DisplayName("E2E: Update Project")
    @Order(3)
    public void testUpdateBoardData(){
        String requestBody = """
                        {
                          "name": "Change project name"
                        }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/api/project/2f5d5fbc-7989-4e2b-8b8a-f2d9eaf58a6c")
                .then()
                .statusCode(200)
                .body("name", equalTo("Change project name"))
                .body("description", equalTo("This is a test project"));
    }

    @Test
    @DisplayName("E2E: Delete Project")
    @Order(4)
    public void testDeleteBoardData(){
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/project/2f5d5fbc-7989-4e2b-8b8a-f2d9eaf58a6c")
                .then()
                .statusCode(200);
    }
}
