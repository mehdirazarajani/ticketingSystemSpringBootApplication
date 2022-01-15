package com.mehdi.ticketingSystem.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.Timestamp;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExtTimestampTest {

    static long baseTimeStamp = 1000;

    private static Stream<Arguments> getArgumentsForTimeExceed() {
        return Stream.of(
                Arguments.of(baseTimeStamp - 100, 0),
                Arguments.of(baseTimeStamp - 50, 0),
                Arguments.of(baseTimeStamp - 10, 0),

                Arguments.of(baseTimeStamp, 0),

                Arguments.of(baseTimeStamp + 10, 10),
                Arguments.of(baseTimeStamp + 50, 50),
                Arguments.of(baseTimeStamp + 100, 100)
        );
    }

    private static Stream<Arguments> getArgumentsForDifference() {
        return Stream.of(
                Arguments.of(baseTimeStamp - 100, -100),
                Arguments.of(baseTimeStamp - 50, -50),
                Arguments.of(baseTimeStamp - 10, -10),

                Arguments.of(baseTimeStamp, 0),

                Arguments.of(baseTimeStamp + 10, 10),
                Arguments.of(baseTimeStamp + 50, 50),
                Arguments.of(baseTimeStamp + 100, 100)
        );
    }

    @DisplayName("Timestamp Time Exceeded")
    @ParameterizedTest(name = "{0}")
    @MethodSource("getArgumentsForTimeExceed")
    void testIsGreaterThanCheck(long toBeChecked, long exceededTime) {
        assertEquals(
                new ExtTimestamp(toBeChecked).timeExceededWithBaseTime(new Timestamp(baseTimeStamp)),
                exceededTime
        );
    }

    @DisplayName("Timestamp Difference")
    @ParameterizedTest(name = "{0}")
    @MethodSource("getArgumentsForDifference")
    void testDifference(long toBeChecked, long exceededTime) {
        assertEquals(
                new ExtTimestamp(toBeChecked).difference(new Timestamp(baseTimeStamp)),
                exceededTime
        );
    }


}