package com.mehdi.ticketingSystem.model.response.common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AbstractResponse {
    protected final String status;
    protected final String message;

    public AbstractResponse(boolean isErrorPresent, String message) {
        this.status = !isErrorPresent ? "success" : "failure";
        this.message = message;
    }

    public boolean isSuccess(){
        return status.equals("success");
    }

}
