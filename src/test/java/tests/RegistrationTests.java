package tests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class RegistrationTests {

    @Test
    public void successfulRegistrationTest() {

        Faker faker = new Faker();
        String username = faker.name().firstName();
        String password = faker.name().firstName();

        String data = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";

        given()
                .log().all()
                .contentType(JSON)
                .body(data)
                .when()
                .post("http://bookclub.qa.guru:8000/api/v1/users/register/")
                .then()
                .log().all()
                .statusCode(201)
                .body("username", is(username))
                .body("id", notNullValue());

    }

    @Test
    public void existingUser400Test() {

        Faker faker = new Faker();
        String username = faker.name().firstName();
        String password = faker.name().firstName();

        String data = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";

        given()
                .log().all()
                .contentType(JSON)
                .body(data)
                .when()
                .post("http://bookclub.qa.guru:8000/api/v1/users/register/")
                .then()
                .log().all()
                .statusCode(201)
                .body("username", is(username))
                .body("id", notNullValue());

        given()
                .log().all()
                .contentType(JSON)
                .body(data)
                .when()
                .post("http://bookclub.qa.guru:8000/api/v1/users/register/")
                .then()
                .log().all()
                .statusCode(400)
                .body("username[0]", is("A user with that username already exists."));

    }
}
