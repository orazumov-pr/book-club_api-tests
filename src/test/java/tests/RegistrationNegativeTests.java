package tests;

import com.github.javafaker.Faker;
import models.registration.RegistrationBodyLombokModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.http.ContentType.JSON;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class RegistrationNegativeTests {

    private static final String BASE_URL = "http://bookclub.qa.guru:8000/api/v1/users/register/";
    private Faker faker;

    private String validUsername;
    private String validPassword;

    @BeforeEach
    void setUp() {
        faker = new Faker();
        validUsername = faker.name().username();
        validPassword = faker.internet().password(8, 20);
    }

    @Test
    @DisplayName("Регистрация с пустым username должна вернуть 400")
    void registrationWithEmptyUsername_shouldReturn400() {
        RegistrationBodyLombokModel data = new RegistrationBodyLombokModel();
        data.setUsername("");
        data.setPassword(validPassword);

        given()
                .log().all()
                .contentType(JSON)
                .body(data)
                .when()
                .post(BASE_URL)
                .then()
                .log().all()
                .statusCode(400)
                .body("username", hasItem(not(emptyString())));
    }

    @Test
    @DisplayName("Регистрация с пустым password должна вернуть 400")
    void registrationWithEmptyPassword_shouldReturn400() {
        RegistrationBodyLombokModel data = new RegistrationBodyLombokModel();
        data.setUsername(validUsername);
        data.setPassword("");

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(400);
    }


}
