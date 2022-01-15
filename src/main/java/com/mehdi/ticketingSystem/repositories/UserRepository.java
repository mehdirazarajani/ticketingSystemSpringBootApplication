package com.mehdi.ticketingSystem.repositories;

import com.mehdi.ticketingSystem.domain.User;
import com.mehdi.ticketingSystem.exceptions.AuthException;

public interface UserRepository {

    Integer create(String firstName, String lastName, String email, String password) throws AuthException;

    User findByEmailAndPassword(String email, String password) throws AuthException;

    Integer getCountByEmail(String email);

    User findById(Integer userId);

}
