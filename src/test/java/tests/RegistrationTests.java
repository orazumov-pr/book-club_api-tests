package tests;

import com.github.javafaker.Faker;
import models.RegistrationBodyLombokModel;
import models.RegistrationResponseLombokModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationTests {

    String username;
    String password;

    @Test
    public void successfulRegistrationTest_bad_practice() {

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

    @Test
     public void successfulRegistrationTest_with_lombok(){
        RegistrationBodyLombokModel data = new RegistrationBodyLombokModel();
        data.setUsername(username);
        data.setUsername(password);

//        RegistrationBodyLombokModel data = new RegistrationBodyLombokModel(username, password);

        RegistrationResponseLombokModel registrationResponse = given()
                .log().all()
                .contentType(JSON)
                .body(data)
                .when()
                .post("http://bookclub.qa.guru:8000/api/v1/users/register/")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .as(RegistrationResponseLombokModel.class);

        assertEquals(username, registrationResponse.getUsername());
    }


}
