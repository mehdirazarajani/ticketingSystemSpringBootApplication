package com.mehdi.ticketingSystem.commonResponses;

import java.util.Map;

public abstract class AbstractResponse {
    private final boolean isErrorPresent;
    protected final String message;

    public AbstractResponse(boolean isErrorPresent, String message) {
        this.isErrorPresent = isErrorPresent;
        this.message = message;
    }

    public boolean isErrorPresent() {
        return isErrorPresent;
    }

}
