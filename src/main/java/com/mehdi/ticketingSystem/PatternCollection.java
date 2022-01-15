package com.mehdi.ticketingSystem;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PatternCollection {

    public final Pattern EMAIL_PATTERN = Pattern.compile("^(.+)@(.+)$");

}
