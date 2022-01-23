package com.mehdi.ticketingSystem.controllers;

import com.google.gson.Gson;
import com.mehdi.ticketingSystem.model.dtos.LoginUserDTO;
import com.mehdi.ticketingSystem.model.dtos.RegisterUserDTO;
import com.mehdi.ticketingSystem.model.response.TokenResponse;
import com.mehdi.ticketingSystem.model.response.common.AbstractResponse;
import com.mehdi.ticketingSystem.model.response.common.ErrorResponse;
import com.mehdi.ticketingSystem.model.response.common.SuccessResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.mehdi.ticketingSystem.AssertionUtils.arrayContentIsSame;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    private Gson gson;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        gson = new Gson();
    }

    @Test
    public void testRegisterFailureIncasOfIncompleteData() throws Exception {
        RegisterUserDTO registerUserDTO = new RegisterUserDTO();
        String randomString = RandomStringUtils.randomAlphabetic(10);
        registerUserDTO.setEmail(randomString + "@gmail.com");
        testRegisterHelper(registerUserDTO, false);

        registerUserDTO = new RegisterUserDTO().setPassword("1234");
        testRegisterHelper(registerUserDTO, false);

        registerUserDTO = new RegisterUserDTO().setFirstName("abc");
        testRegisterHelper(registerUserDTO, false);

        registerUserDTO = new RegisterUserDTO().setLastName("abc");
        testRegisterHelper(registerUserDTO, false);

        registerUserDTO = new RegisterUserDTO();
        try {
            ErrorResponse errorResponse = (ErrorResponse) testRegisterHelper(registerUserDTO, false);
            arrayContentIsSame(
                    errorResponse.getErrors(),
                    List.of(
                            "The email is required.",
                            "The password is required.",
                            "The firstName is required.",
                            "The lastName is required."
                    )
            );
        } catch (Exception e) {
            fail("Invalid Response");
        }
    }

    @Test
    public void testRegisterFailureIncasOfIncompleteEmail() {
        RegisterUserDTO registerUserDTO = new RegisterUserDTO();
        String randomString = RandomStringUtils.randomAlphabetic(10);
        registerUserDTO.setEmail(randomString).
                setFirstName("Mehdi").
                setLastName("Raza").
                setPassword("12345");
        try {
            ErrorResponse errorResponse = (ErrorResponse) testRegisterHelper(registerUserDTO, false);
            arrayContentIsSame(
                    errorResponse.getErrors(),
                    List.of("The email address is invalid.")
            );
        } catch (Exception e) {
            fail("Invalid Response");
        }
    }

    @Test
    public void testRegisterSuccessful() throws Exception {

        RegisterUserDTO registerUserDTO = new RegisterUserDTO();
        String randomString = RandomStringUtils.randomAlphabetic(10);
        registerUserDTO.setEmail(randomString + "@gmail.com").
                setFirstName("Mehdi").
                setLastName("Raza").
                setPassword("12345");
        try {
            SuccessResponse<TokenResponse> response = (SuccessResponse<TokenResponse>) testRegisterHelper(registerUserDTO, true);
            assertNotNull(response.getData());
        } catch (Exception e) {
            fail("Invalid Response");
        }
    }

    @Test
    public void testRegisterFailedBecauseOfRepeatedEmail() throws Exception {
        RegisterUserDTO registerUserDTO = new RegisterUserDTO();
        String randomString = RandomStringUtils.randomAlphabetic(10);
        registerUserDTO.setEmail(randomString + "@gmail.com").
                setFirstName("Mehdi").
                setLastName("Raza").
                setPassword("12345");
        testRegisterHelper(registerUserDTO, true);
        // try to add same user details and it should fail
        testRegisterHelper(registerUserDTO, false);
    }

    @Test
    public void testLoginFailureIncasOfIncompleteData() throws Exception {
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        String randomString = RandomStringUtils.randomAlphabetic(10);
        loginUserDTO.setEmail(randomString + "@gmail.com");
        testLoginHelper(loginUserDTO, false);

        loginUserDTO = new LoginUserDTO().setPassword("1234");
        testLoginHelper(loginUserDTO, false);

        loginUserDTO = new LoginUserDTO();
        try {
            ErrorResponse errorResponse = (ErrorResponse) testLoginHelper(loginUserDTO, false);
            arrayContentIsSame(
                    errorResponse.getErrors(),
                    List.of(
                            "The email is required.",
                            "The password is required."
                    )
            );
        } catch (Exception e) {
            fail("Invalid Response");
        }
    }

    @Test
    public void testLoginFailureIncasOfInvalidEmail() throws Exception{
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        String randomString = RandomStringUtils.randomAlphabetic(10);
        loginUserDTO.setEmail(randomString).setPassword("1234");
        try {
            ErrorResponse errorResponse = (ErrorResponse) testLoginHelper(loginUserDTO, false);
            arrayContentIsSame(
                    errorResponse.getErrors(),
                    List.of("The email address is invalid.")
            );
        } catch (Exception e) {
            fail("Invalid Response");
        }
    }

    @Test
    public void testLoginFailureIncasOfDataNotPresentInDB() throws Exception{
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        String randomString = RandomStringUtils.randomAlphabetic(10);
        loginUserDTO.setEmail(randomString + "@yahoo.com").setPassword("1234");
        try {
            ErrorResponse errorResponse = (ErrorResponse) testLoginHelper(loginUserDTO, false);
            arrayContentIsSame(
                    errorResponse.getErrors(),
                    List.of("Invalid email/password")
            );
        } catch (Exception e) {
            fail("Invalid Response");
        }

        loginUserDTO = new LoginUserDTO();
        loginUserDTO.setEmail("mehdi@gmail.com").setPassword("mehdi");
        try {
            ErrorResponse errorResponse = (ErrorResponse) testLoginHelper(loginUserDTO, false);
            arrayContentIsSame(
                    errorResponse.getErrors(),
                    List.of("Invalid email/password")
            );
        } catch (Exception e) {
            fail("Invalid Response");
        }
    }

    @Test
    public void testLoginSuccessful() throws Exception {
        RegisterUserDTO registerUserDTO = new RegisterUserDTO();
        registerUserDTO.setEmail("mehdi@gmail.com").
                setFirstName("Mehdi").
                setLastName("Raza").
                setPassword("123456");
        String jsonRequest = gson.toJson(registerUserDTO);
        mockMvc.perform(post("/api/users/register").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setEmail("mehdi@gmail.com").setPassword("123456");
        try {
            SuccessResponse<TokenResponse> response = (SuccessResponse<TokenResponse>) testLoginHelper(loginUserDTO, true);
            assertNotNull(response.getData());
        } catch (Exception e) {
            fail("Invalid Response");
        }
    }

     AbstractResponse testRegisterHelper(RegisterUserDTO registerUserDTO, boolean isSucceeded) throws Exception {
        String jsonRequest = gson.toJson(registerUserDTO);
        MvcResult result = mockMvc.perform(post("/api/users/register").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        String resultContent = result.getResponse().getContentAsString();

        if (isSucceeded) {
            SuccessResponse response = gson.fromJson(resultContent, SuccessResponse.class);
            assertTrue(response.isSuccess(), response.getMessage());
            return response;
        }
        ErrorResponse response = gson.fromJson(resultContent, ErrorResponse.class);
        assertFalse(response.isSuccess(), response.getMessage());
        return response;
    }

    AbstractResponse testLoginHelper(LoginUserDTO registerUserDTO, boolean isSucceeded) throws Exception {
        String jsonRequest = gson.toJson(registerUserDTO);
        MvcResult result = mockMvc.perform(post("/api/users/login").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        String resultContent = result.getResponse().getContentAsString();

        if (isSucceeded) {
            SuccessResponse response = gson.fromJson(resultContent, SuccessResponse.class);
            assertTrue(response.isSuccess(), response.getMessage());
            return response;
        }
        ErrorResponse response = gson.fromJson(resultContent, ErrorResponse.class);
        assertFalse(response.isSuccess(), response.getMessage());
        return response;
    }


}