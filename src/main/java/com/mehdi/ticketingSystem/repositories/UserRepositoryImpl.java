package com.mehdi.ticketingSystem.repositories;

import com.mehdi.ticketingSystem.model.User;
import com.mehdi.ticketingSystem.exceptions.AuthException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {

    public static final String SELECT_ALL = "SELECT " + User.USER_ID + ", " + User.FIRST_NAME + ", " + User.LAST_NAME + ", " + User.EMAIL + ", " + User.PASSWORD + " " +
            "FROM " + User.TABLE_NAME;

    private static final String SQL_CREATE = "INSERT INTO " + User.TABLE_NAME + " (" + User.FIRST_NAME + ", " + User.LAST_NAME + ", " + User.EMAIL + ", " + User.PASSWORD + ") " +
            "VALUES(?, ?, ?, ?)";
    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM " + User.TABLE_NAME + " WHERE " + User.EMAIL + " = ?";
    private static final String SQL_FIND_BY_ID = SELECT_ALL + " WHERE " + User.USER_ID + " = ?";
    private static final String SQL_FIND_BY_EMAIL = SELECT_ALL + " WHERE " + User.EMAIL + " = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(String firstName, String lastName, String email, String password) throws AuthException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, email);
                ps.setString(4, hashedPassword);
                return ps;
            }, keyHolder);
            Map<String, Object> keys = keyHolder.getKeys();
            if (keys == null || !keys.containsKey(User.USER_ID))
                throw new AuthException("Invalid details. Failed to create account");
            return (Integer) keys.get(User.USER_ID);
        } catch (Exception e) {
            throw new AuthException("Invalid details. Failed to create account");
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws AuthException {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, userRowMapper);
            if (user == null) {
                throw new AuthException("Invalid email/password");
            }
            String correctPassword = user.getPassword();
            if (!BCrypt.checkpw(password, correctPassword))
                throw new AuthException("Invalid email/password");
            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new AuthException("Invalid email/password");
        }
    }

    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);
    }

    @Override
    public User findById(Integer userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId}, userRowMapper);
    }

    private final RowMapper<User> userRowMapper = ((rs, rowNum) ->
            new User().
                    setUserId(rs.getInt(User.USER_ID)).
                    setFirstName(rs.getString(User.FIRST_NAME)).
                    setLastName(rs.getString(User.LAST_NAME)).
                    setEmail(rs.getString(User.EMAIL)).
                    setPassword(rs.getString(User.PASSWORD))
    );
}
