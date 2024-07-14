package com.neptune.boards.tests.e2e;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BoardE2ETest {

    @Test
    @DisplayName("E2E: Create Board")
    @Order(1)
    public void testCreateBoard(){
        String requestBody = """
            {
              "name": "Test Board",
              "description": "This is a test board"
            }
         """;
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/board/2f5d5fbc-7989-4e2b-8b8a-f2d9eaf58a6c")
                .then()
                .statusCode(200)
                .body("name", equalTo("Test Board"))
                .body("description", equalTo("This is a test board"));
    }

    @Test
    @DisplayName("E2E: Get Board by UUID")
    @Order(2)
    public void testGetBoardData(){
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/board/2f5d5fbc-7989-4e2b-8b8a-f2d9eaf58a6c")
                .then()
                .statusCode(200)
                .body("name", equalTo("Test Board"))
                .body("description", equalTo("This is a test board"));
    }

    @Test
    @DisplayName("E2E: Update Board")
    @Order(3)
    public void testUpdateBoardData(){
        String requestBody = """
                        {
                          "name": "Change board name"
                        }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/api/board/2f5d5fbc-7989-4e2b-8b8a-f2d9eaf58a6c")
                .then()
                .statusCode(200)
                .body("name", equalTo("Change board name"))
                .body("description", equalTo("This is a test board"));
    }

    @Test
    @DisplayName("E2E: Delete Board")
    @Order(4)
    public void testDeleteBoardData(){
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/board/2f5d5fbc-7989-4e2b-8b8a-f2d9eaf58a6c")
                .then()
                .statusCode(200);
    }
}
