package com.mehdi.ticketingSystem.model.dtos;

import javax.validation.constraints.*;

import com.mehdi.ticketingSystem.model.User;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RegisterUserDTO {

    @NotBlank(message = "The firstName is required.")
    private String firstName;

    @NotBlank(message = "The lastName is required.")
    private String lastName;

    @NotBlank(message = "The email is required.")
    @Email(message = "The email address is invalid.", flags = { Pattern.Flag.CASE_INSENSITIVE })
    private String email;

    @NotBlank(message = "The password is required.")
    private String password;

    User toUser(){
        return new User().setFirstName(firstName).setLastName(lastName).setEmail(email).setPassword(password);
    }

}
