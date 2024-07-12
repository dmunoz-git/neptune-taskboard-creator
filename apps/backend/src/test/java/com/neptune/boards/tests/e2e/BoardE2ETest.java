package com.neptune.boards.tests.e2e;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BoardE2ETest {

    @Test
    public void testCreateBoardWithTemplate(){
        String requestBody = """
                        {
                          "name": "Test board with Template",
                          "uuid": "2f5d5fbc-7989-4e2b-8b8a-f2d9eaf58a6c",
                          "description": "This is a test board",
                          "template": "Scrumban"
                        }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/board")
                .then()
                .statusCode(200)
                .body("name", equalTo("Test Board"))
                .body("description", equalTo("This is a testboard"))
                .body("templateStates.size()", equalTo(4))
                .body("templateStates", hasItems("Backlog", "To Do", "Doing", "In Progress", "Review", "Done"));
    }

    @Test
    public void testCreateBoardWithoutTemplate() {
        String requestBody = """
                        {
                          "name": "Test board without Template",
                          "uuid": "96a72479-8884-40a1-bdc1-cd85783fc173",
                          "description": "This is a test board",
                        }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/board")
                .then()
                .statusCode(200)
                .body("name", equalTo("Test Board"))
                .body("description", equalTo("This is a testboard"))
                .body("templateStates.size()", equalTo(4))
                .body("templateStates", hasItems("To Do", "Doing", "In Progress", "Done"));
    }

    @Test
    public void testGetBoardData(){
        given()
                .contentType(ContentType.JSON)
                .param("uuid", "96a72479-8884-40a1-bdc1-cd85783fc173")
                .when()
                .get("/api/board/{uuid}")
                .then()
                .statusCode(200)
                .body("name", notNullValue())
                .body("description", notNullValue())
                .body("templateStates.size()", equalTo(4))
                .body("tasks", emptyArray());
    }

    @Test
    public void testUpdateBoardData(){
        String requestBody = """
                        {
                          "name": "Change board name",
                          "description": "This is a test board mod",
                        }
                """;

        given()
                .contentType(ContentType.JSON)
                .param("uuid", "96a72479-8884-40a1-bdc1-cd85783fc173")
                .when()
                .put("/api/board/{uuid}")
                .then()
                .statusCode(200)
                .body("name", equalTo("Change board name"))
                .body("description", equalTo("This is a test board mod"));
    }
}
