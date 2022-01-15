package com.mehdi.ticketingSystem.error;

public class ErrorCondition {
    String message;
    boolean condition;

    public ErrorCondition(String message, boolean condition) {
        this.message = message;
        this.condition = condition;
    }

    public static ErrorCondition init(boolean condition, String fieldName, String message){
        return new ErrorCondition(fieldName + " " + message, condition);
    }
    public static ErrorCondition init(boolean condition, String message){
        return new ErrorCondition(message, condition);
    }

}
