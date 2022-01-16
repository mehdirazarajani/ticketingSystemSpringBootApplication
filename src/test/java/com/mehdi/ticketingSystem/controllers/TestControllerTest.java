package com.mehdi.ticketingSystem.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mehdi.ticketingSystem.ConstantManager;
import com.mehdi.ticketingSystem.model.dtos.LoginUserDTO;
import com.mehdi.ticketingSystem.model.response.TokenResponse;
import com.mehdi.ticketingSystem.model.response.common.SuccessResponse;
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

import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class TestControllerTest {

    private Gson gson;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        gson = new Gson();
    }

    @Test
    public void testFailureIfAuthenticationIsAbsent() throws Exception {
        mockMvc.perform(get("/api/test/test")
                .header("ABC", "XYZ")).andExpect(status().isForbidden());
    }

    @Test
    public void testFailureIfToken() throws Exception {
        mockMvc.perform(get("/api/test/test")
                .header(ConstantManager.getInstance().AUTH_KEY, "xyz")).andExpect(status().isForbidden());
    }

    @Test
    public void testSuccessfulAuthorization() throws Exception {
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setEmail("mehdi@gmail.com").setPassword("123456");
        String jsonRequest = gson.toJson(loginUserDTO);
        MvcResult result = mockMvc.perform(post("/api/users/login").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        String resultContent = result.getResponse().getContentAsString();

        try {
            Type type = new TypeToken<SuccessResponse<TokenResponse>>() {}.getType();

            SuccessResponse<TokenResponse> response = gson.fromJson(resultContent, type);
            assertTrue(response.isSuccess(), response.getMessage());

            String token = response.getData().getToken();
            assertNotNull(token);

            mockMvc.perform(get("/api/test/test")
                            .header(ConstantManager.getInstance().AUTH_KEY, token)
                            .content("{}").contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            fail("Invalid Response");
        }
    }

}