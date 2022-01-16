package com.mehdi.ticketingSystem.model.response.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ErrorResponse extends AbstractResponse{

    List<String> errors;

    public ErrorResponse(String message, List<String> errors) {
        super(true, message);
        this.errors = errors;
    }

}
