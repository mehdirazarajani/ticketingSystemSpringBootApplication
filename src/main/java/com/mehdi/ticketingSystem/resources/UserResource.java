package com.mehdi.ticketingSystem.resources;

import com.mehdi.ticketingSystem.ConstantManager;
import com.mehdi.ticketingSystem.error.*;
import com.mehdi.ticketingSystem.commonResponses.*;
import com.mehdi.ticketingSystem.domain.User;
import com.mehdi.ticketingSystem.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody Map<String, Object> userMap) {

        List<ErrorCondition> errorConditions = List.of(
                ErrorCondition.init(!userMap.containsKey(User.EMAIL), User.EMAIL, ErrorMessage.FIELD_MISSING),
                ErrorCondition.init(!userMap.containsKey(User.PASSWORD), User.PASSWORD, ErrorMessage.FIELD_MISSING)
        );

        AbstractResponse errorIfAny = ErrorManager.getInstance().getErrorMessage(errorConditions, "");
        if (errorIfAny.isErrorPresent()){
            return new ResponseEntity<>(errorIfAny, HttpStatus.BAD_REQUEST);
        }

        String email = (String) userMap.get(User.EMAIL);
        String password = (String) userMap.get(User.PASSWORD);
        User user = userService.validateUser(email, password);
        return new ResponseEntity<>(
                new SuccessResponse<>("Customer successfully logged in", generateJWTToken(user)),
                HttpStatus.OK
        );
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody Map<String, Object> userMap) {

        List<ErrorCondition> errorConditions = List.of(
                ErrorCondition.init(!userMap.containsKey(User.FIRST_NAME), User.FIRST_NAME, ErrorMessage.FIELD_MISSING),
                ErrorCondition.init(!userMap.containsKey(User.LAST_NAME), User.LAST_NAME, ErrorMessage.FIELD_MISSING),
                ErrorCondition.init(!userMap.containsKey(User.EMAIL), User.EMAIL, ErrorMessage.FIELD_MISSING),
                ErrorCondition.init(!userMap.containsKey(User.PASSWORD), User.PASSWORD, ErrorMessage.FIELD_MISSING)
        );

        var errorIfAny = ErrorManager.getInstance().getErrorMessage(errorConditions, "");
        if (errorIfAny.isErrorPresent()){
            return new ResponseEntity<>(errorIfAny, HttpStatus.BAD_REQUEST);
        }

        try {
            String firstName = (String) userMap.get(User.FIRST_NAME);
            String lastName = (String) userMap.get(User.LAST_NAME);
            String email = (String) userMap.get(User.EMAIL);
            String password = (String) userMap.get(User.PASSWORD);
            User user = userService.registerUser(firstName, lastName, email, password);
            return new ResponseEntity<Map<String, String>> (
                    new SuccessResponse<> ("Customer is created successfully", generateJWTToken(user)),
                    HttpStatus.OK
            );
        } catch (Exception ex) {
            return new ResponseEntity<>(ErrorResponse.create(ex.getMessage()).getResponse(), HttpStatus.FORBIDDEN);
        }
    }

    private Map<String, String> generateJWTToken(User user) {
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
        return Map.of("token", token);
    }
}
