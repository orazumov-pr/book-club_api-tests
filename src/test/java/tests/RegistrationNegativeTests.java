package tests;

import com.github.javafaker.Faker;
import models.registration.RegistrationBodyLombokModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.http.ContentType.JSON;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class RegistrationNegativeTests {

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
    void emptyUsername400Test() {
        RegistrationBodyLombokModel data = new RegistrationBodyLombokModel();
        data.setUsername("");
        data.setPassword(validPassword);

        given()
                .log().all()
                .contentType(JSON)
                .body(data)
                .when()
                .post(baseURI)
                .then()
                .log().all()
                .statusCode(400)
                .body("username", hasItem(not(emptyString())));
    }

    @Test
    @DisplayName("Регистрация с пустым password должна вернуть 400")
    void emptyPassword400Test() {
        RegistrationBodyLombokModel data = new RegistrationBodyLombokModel();
        data.setUsername(validUsername);
        data.setPassword("");

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post(baseURI)
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("GET запрос на эндпоинт регистрации возвпащает 405")
    void getRequestToRegistration_shouldReturn405() {
        given()
                .when()
                .get(baseURI)
                .then()
                .statusCode(405);
    }


}
