package com.mehdi.ticketingSystem;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssertionUtils {

    public static void arrayContentIsSame(List result, List expected){
        assertEquals(result.size(), expected.size());
        assertTrue(result.containsAll(expected) && expected.containsAll(result));
    }
}
