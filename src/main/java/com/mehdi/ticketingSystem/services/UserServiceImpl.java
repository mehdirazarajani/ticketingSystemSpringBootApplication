package com.mehdi.ticketingSystem.services;

import com.mehdi.ticketingSystem.PatternCollection;
import com.mehdi.ticketingSystem.model.User;
import com.mehdi.ticketingSystem.exceptions.AuthException;
import com.mehdi.ticketingSystem.repositories.UserRepository;
import com.mehdi.ticketingSystem.services.interfaces.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PatternCollection patternCollection;

    public UserServiceImpl(UserRepository userRepository, PatternCollection patternCollection) {
        this.userRepository = userRepository;
        this.patternCollection = patternCollection;
    }

    @Override
    public User validateUser(String email, String password) throws AuthException {
        if(email != null) email = email.toLowerCase();
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User registerUser(String firstName, String lastName, String email, String password) throws AuthException {
        if (email == null)
            throw new AuthException("Invalid email format");
        email = email.toLowerCase();
        if (!patternCollection.EMAIL_PATTERN.matcher(email).matches())
            throw new AuthException("Invalid email format");
        Integer count = userRepository.getCountByEmail(email);
        if (count > 0)
            throw new AuthException("Email already in use");
        Integer userId = userRepository.create(firstName, lastName, email, password);
        return userRepository.findById(userId);
    }
}
