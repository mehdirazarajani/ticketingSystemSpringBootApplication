package com.mehdi.ticketingSystem.commonResponses;

import java.util.Map;

public class SuccessResponse<T> extends AbstractResponse {

    T data;

    public SuccessResponse(String message) {
        super(false, message);
    }

    public SuccessResponse(String message, T data) {
        super(false, message);
        this.data = data;
    }

    @Override
    public Map<String, Object> getResponse() {
        if (data == null)
            return Map.of("status", "success", "message", message);
        return Map.of("status", "success", "message", message, "data", data);
    }
}
