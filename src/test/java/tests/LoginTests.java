package tests;

import models.login.LoginBodyModel;
import models.login.SuccessfulLoginResponseModel;
import models.login.WrongCredentialsLoginResponseModel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.notNullValue;


public class LoginTests extends TestBase {

    String username = "qaguru";
    String password = "qaguru123";
    String wrongPassword = "qaguru1234";

    @Test
    @Disabled
    public void successfulLoginTest(){
        LoginBodyModel loginData = new LoginBodyModel(username, password);

        SuccessfulLoginResponseModel loginResponse = given()
                .log().all()
                .contentType(JSON)
                .body(loginData)
                .basePath("/api/v1")
                .when()
                .post("/auth/token/")
                .then()
                .log().all()
                .statusCode(200)
                .body("access", notNullValue())
                .body("refresh", notNullValue())
                .extract().as(SuccessfulLoginResponseModel.class);

        String expectedTokenPath = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
        String actualAccess = loginResponse.access();
        String actualRefresh = loginResponse.refresh();


    }

    @Test
    @Disabled
    public void wrongCredentialsLoginTest(){
        LoginBodyModel loginData = new LoginBodyModel(username, wrongPassword);

        WrongCredentialsLoginResponseModel loginResponse = given()
                .log().all()
                .contentType(JSON)
                .body(loginData)
                .basePath("/api/v1")
                .when()
                .post("/auth/token/")
                .then()
                .log().all()
                .statusCode(401)
                .body("detail", notNullValue())
                .extract().as(WrongCredentialsLoginResponseModel.class);

        String expectedDetailError = "Invalid username or password.";
        String actualDetailError = loginResponse.detail();


    }

    private Object assertThat(String actualDetailError) {
    }
}
