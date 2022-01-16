package com.mehdi.ticketingSystem.model.response.common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SuccessResponse<T> extends AbstractResponse {

    T data;

    public SuccessResponse(String message, T data) {
        super(false, message);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
