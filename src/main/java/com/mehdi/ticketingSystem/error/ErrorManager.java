package com.mehdi.ticketingSystem.error;

import com.mehdi.ticketingSystem.commonResponses.*;

import java.util.List;

public class ErrorManager {

    static ErrorManager instance;

    private ErrorManager() {
    }

    public static ErrorManager getInstance() {
        if (instance == null)
            instance = new ErrorManager();
        return instance;
    }

    public AbstractResponse getErrorMessage(List<ErrorCondition> errorConditions, String successMessage) {
        for (ErrorCondition errorCondition : errorConditions) {
            if (errorCondition.condition)
                return ErrorResponse.create(errorCondition.message);
        }
        return new SuccessResponse(successMessage);
    }

}
