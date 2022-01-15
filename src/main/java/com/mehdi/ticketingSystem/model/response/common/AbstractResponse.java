package com.mehdi.ticketingSystem.model.response.common;

public abstract class AbstractResponse {
    protected String status;
    protected final String message;

    public AbstractResponse(boolean isErrorPresent, String message) {
        this.status = !isErrorPresent ? "success" : "failure";
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }
}
