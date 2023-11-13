import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class controllerPost {
    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    public void postMethod() {
        RestAssured.given()
        .body("{\"nome\": \"Pauler\", \"quantidade\": 10, \"valor\": 7.00}")
        .contentType(ContentType.JSON)
          .when()
        .post("/")
          .then()
        .statusCode(201)

        .body("nome", Matchers.is("Pauler"))
        .body("quantidade", Matchers.is(10))
        .body("valor", Matchers.is(7.00f));
    }
}
