package com.mehdi.ticketingSystem.controllers;

import com.mehdi.ticketingSystem.model.response.common.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<SuccessResponse<Object>> test(@RequestBody Map<String, Object> userMap) {
        return new ResponseEntity<>(new SuccessResponse<>("the request is successful", null), HttpStatus.OK);
    }

}
