package com.mehdi.ticketingSystem.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/sample1")
public class Sample1Resource {

    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> test(@RequestBody Map<String, Object> userMap) {
        return new ResponseEntity<>(Collections.emptyMap(), HttpStatus.OK);
    }

}
