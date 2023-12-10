package br.com.codeinloop;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class TodoControllerTest {

    @Test
    public void testReadAllEndpoint() {
        String todoJson = "{ \"title\": \"Test Todo\", \"completed\": false, \"order\": 1 }";
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(todoJson)
                .when().post("/todo")
                .then()
                .statusCode(201);

        given()
                .when().get("/todo")
                .then()
                .statusCode(200)
                .body("[0].title", equalTo("Test Todo"));
    }
    @Test
    public void testReadOneNotFound() {
        long nonExistentId = 999L;

        given()
                .when().get("/todo/" + nonExistentId)
                .then()
                .statusCode(404);
    }

    @Test
    public void testCreateEndpoint() {
        String todoJson = "{ \"title\": \"Test Todo\", \"completed\": false, \"order\": 1 }";

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(todoJson)
                .when().post("/todo")
                .then()
                .statusCode(201)
                .body("title", is("Test Todo"));
    }

    @Test
    public void testReadOneEndpoint() {
        String todoJson = "{ \"title\": \"Test Todo\", \"completed\": false, \"order\": 1 }";

        long todoId = given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(todoJson)
                .when().post("/todo")
                .then()
                .extract().jsonPath().getLong("id");

        given()
                .when().get("/todo/" + todoId)
                .then()
                .statusCode(200)
                .body("title", is("Test Todo"));
    }

    @Test
    public void testDeleteOneEndpoint() {
        String todoJson = "{ \"title\": \"Delete Todo\", \"completed\": false, \"order\": 1 }";

        long todoId = given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(todoJson)
                .when().post("/todo")
                .then()
                .extract().jsonPath().getLong("id");

        given()
                .when().delete("/todo/" + todoId)
                .then()
                .statusCode(204);
    }


    @Test
    public void testDeleteAllEndpoint() {
        given()
                .when().delete("/todo")
                .then()
                .statusCode(204);
    }
    @Test
    public void testFullUpdateEndpoint() {
        String newTodoJson = "{ \"title\": \"Original Todo\", \"completed\": false, \"order\": 1 }";
        long todoId = given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(newTodoJson)
                .when().post("/todo")
                .then()
                .extract().jsonPath().getLong("id");

        String updatedTodoJson = "{ \"title\": \"Updated Todo\", \"completed\": true, \"order\": 2 }";
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(updatedTodoJson)
                .when().put("/todo/" + todoId)
                .then()
                .statusCode(200)
                .body("title", is("Updated Todo"))
                .body("completed", is(true))
                .body("order", is(2));

        given()
                .when().get("/todo/" + todoId)
                .then()
                .statusCode(200)
                .body("title", is("Updated Todo"))
                .body("completed", is(true))
                .body("order", is(2));
    }
    @Test
    public void testPartialUpdateEndpoint() {
        String newTodoJson = "{ \"title\": \"Original Todo\", \"completed\": false, \"order\": 1 }";
        long todoId = given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(newTodoJson)
                .when().post("/todo")
                .then()
                .extract().jsonPath().getLong("id");

        String partialUpdateJson = "{ \"title\": \"Partially Updated Todo\" }";
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(partialUpdateJson)
                .when().patch("/todo/" + todoId)
                .then()
                .statusCode(200)
                .body("title", is("Partially Updated Todo"))
                .body("completed", is(false))
                .body("order", is(1));

        given()
                .when().get("/todo/" + todoId)
                .then()
                .statusCode(200)
                .body("title", is("Partially Updated Todo"))
                .body("completed", is(false))
                .body("order", is(1));
    }
}
