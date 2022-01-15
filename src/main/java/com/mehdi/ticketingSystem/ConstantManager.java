package com.mehdi.ticketingSystem;

public class ConstantManager {

    static ConstantManager instance;

    private ConstantManager(){}

    public static ConstantManager getInstance() {
        if (instance == null)
            instance = new ConstantManager();
        return instance;
    }

    public final String AUTH_KEY = "Authorization";
    public final String API_SECRET_KEY = "passwordIsToHard";
    public  final long TOKEN_VALIDITY = 2 * 60 * 60 * 1000;

}
