package com.mehdi.ticketingSystem.model.response.common;

import java.util.List;


public class ErrorResponse extends AbstractResponse{

    List<String> errors;

    public ErrorResponse(String message, List<String> errors) {
        super(true, message);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
