package tests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class RegistrationTests {

    @Test
    public void successfulRegistrationTest{

        Faker faker = new Faker();

        //body {
        // "username":"oleg",
        // "password":"12345"}
        // http://bookclub.qa.guru:8000/api/v1/user/register/

        given()

    }


}
