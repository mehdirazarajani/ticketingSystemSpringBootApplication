package com.mehdi.ticketingSystem.controllers;

import com.mehdi.ticketingSystem.ConstantManager;
import com.mehdi.ticketingSystem.model.response.common.SuccessResponse;
import com.mehdi.ticketingSystem.model.User;
import com.mehdi.ticketingSystem.model.dtos.LoginUserDTO;
import com.mehdi.ticketingSystem.model.dtos.RegisterUserDTO;
import com.mehdi.ticketingSystem.model.response.TokenResponse;
import com.mehdi.ticketingSystem.services.interfaces.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<TokenResponse>> loginUser(@Valid @RequestBody LoginUserDTO loginUserDTO) {

        User user = userService.validateUser(loginUserDTO.getEmail(), loginUserDTO.getPassword());
        return new ResponseEntity<>(
                new SuccessResponse<>("Customer successfully logged in", generateJWTToken(user)),
                HttpStatus.OK
        );
    }

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<TokenResponse>> registerUser(@Valid @RequestBody RegisterUserDTO registerUserDto) {
        
        User user = userService.registerUser(registerUserDto.getFirstName(), registerUserDto.getLastName(), registerUserDto.getEmail(), registerUserDto.getPassword());
        return new ResponseEntity<>(
                new SuccessResponse<>("Customer is created successfully", generateJWTToken(user)),
                HttpStatus.OK
        );
    }

    private TokenResponse generateJWTToken(User user) {
        ConstantManager constantManager = ConstantManager.getInstance();
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, constantManager.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + constantManager.TOKEN_VALIDITY))
                .claim(User.USER_ID, user.getUserId())
                .claim(User.EMAIL, user.getEmail())
                .claim(User.FIRST_NAME, user.getFirstName())
                .claim(User.LAST_NAME, user.getLastName())
                .compact();
        return new TokenResponse(token);
    }
}
