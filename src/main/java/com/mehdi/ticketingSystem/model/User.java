package com.mehdi.ticketingSystem.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "users")
@Entity
@Data
@Accessors(chain = true)
public class User {

    public static String TABLE_NAME = "USERS";
    public static String USER_ID = "user_id";
    public static String FIRST_NAME = "first_name";
    public static String LAST_NAME = "last_name";
    public static String EMAIL = "email";
    public static String PASSWORD = "password";

    @Column(nullable = false)
    private Integer userId;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

}
