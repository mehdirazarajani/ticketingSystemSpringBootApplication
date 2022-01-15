package com.mehdi.ticketingSystem.services.interfaces;

import com.mehdi.ticketingSystem.model.User;
import com.mehdi.ticketingSystem.exceptions.AuthException;

public interface UserService {

    User validateUser(String email, String password) throws AuthException;

    User registerUser(String firstName, String lastName, String email, String password) throws AuthException;

}
