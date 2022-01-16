package com.mehdi.ticketingSystem.model.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenResponse {
    String token;

    public TokenResponse(String token) {
        this.token = token;
    }

}
