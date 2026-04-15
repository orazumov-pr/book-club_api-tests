package tests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class RegistrationTests {

    @Test
    public void successfulRegistrationTest() {

        Faker faker = new Faker();
        String username = faker.name().fullName();
        String password = faker.name().firstName();

        String data = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";

        //body {
        // "username":"oleg",
        // "password":"12345"}
        // http://bookclub.qa.guru:8000/api/v1/user/register/

        given()
                .body(data)
                .when()
                .post("http://bookclub.qa.guru:8000/api/v1/user/register/")
                .then()
                .statusCode(201)
                .body("username", is(username))
                .body("id", notNullValue());

    }


}
