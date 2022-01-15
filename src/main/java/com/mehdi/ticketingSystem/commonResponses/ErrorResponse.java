package com.mehdi.ticketingSystem.commonResponses;

import java.util.Map;

public class ErrorResponse extends AbstractResponse{
    public ErrorResponse(String message) {
        super(true, message);
    }

    public static ErrorResponse create(String message){
        return new ErrorResponse(message);
    }

    @Override
    public Map<String, Object> getResponse() {
        return Map.of("status", "failure", "message", message);
    }
}
