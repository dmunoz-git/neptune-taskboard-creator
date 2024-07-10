package com.neptune.taskboard.test.e2e;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@SpringBootTest
public class TaskboardE2ETest {

    @Test
    public void testCreateTask(){
        given()
                .contentType(ContentType.JSON)
                .queryParam("dashboardId", 1)
                .when()
                .post("/api/task")
                .then()
                .statusCode(201)
                .body("name", equalTo("Test Task"))
                .body("description", equalTo("Test Description"))
                .body("dashboard.id", equalTo(1));
    }
}
