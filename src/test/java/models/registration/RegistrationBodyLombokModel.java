package models.registration;

import lombok.Data;

@Data
public class RegistrationBodyLombokModel {
    String username;
    String password;

    String validUsername;
    String validPassword;
}

