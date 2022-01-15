package com.mehdi.ticketingSystem.services;

import com.mehdi.ticketingSystem.domain.User;
import com.mehdi.ticketingSystem.exceptions.AuthException;

public interface UserService {

    User validateUser(String email, String password) throws AuthException;

    User registerUser(String firstName, String lastName, String email, String password) throws AuthException;

}
