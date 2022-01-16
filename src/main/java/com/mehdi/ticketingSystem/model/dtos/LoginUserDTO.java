package com.mehdi.ticketingSystem.model.dtos;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Accessors(chain = true)
public class LoginUserDTO {

    @NotEmpty(message = "The email is required.")
    @Email(message = "The email address is invalid.", flags = { Pattern.Flag.CASE_INSENSITIVE })
    private String email;

    @NotBlank(message = "The password is required.")
    private String password;

}
