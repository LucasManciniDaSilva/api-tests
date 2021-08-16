package tasks.apiTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

public class APITest {

  @BeforeClass
  public static void setup(){
    RestAssured.baseURI = "http://localhost:8001/tasks-backend";
  }

  @Test
  public void shouldReturnTasks(){
    RestAssured.given()
        .when()
          .get("/todo")
        .then()
          .statusCode(200);
  }

  @Test
  public void shouldAddTaskSuccessfully(){
    RestAssured.given()
        .body(
            "{\n" + "  \"task\": \"Teste via Api\",\n" + "    \"dueDate\": \"2021-12-30\"\n" + "}")
        .contentType(ContentType.JSON)
        .when()
          .post("/todo")
        .then()
          .statusCode(201);
  }

  @Test
  public void shouldNotAddFailedTask(){
    RestAssured.given()
          .body(
            "{\n" + "  \"task\": \"Teste via Api\",\n" + "    \"dueDate\": \"2020-12-30\"\n" + "}")
          .contentType(ContentType.JSON)
        .when()
          .post("/todo")
        .then()
          .statusCode(400)
          .body("message", CoreMatchers.is("Due date must not be in past"));
  }

}

