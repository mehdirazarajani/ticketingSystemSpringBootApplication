package com.mehdi.ticketingSystem.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTypeTest {


    private static Stream<Arguments> getArgumentsForGreaterThanCheck() {
        return Stream.of(

                Arguments.of(CustomerType.NEW, CustomerType.NEW, false),
                Arguments.of(CustomerType.NEW, CustomerType.LOYAL, false),
                Arguments.of(CustomerType.NEW, CustomerType.VIP, false),

                Arguments.of(CustomerType.LOYAL, CustomerType.NEW, true),
                Arguments.of(CustomerType.LOYAL, CustomerType.LOYAL, false),
                Arguments.of(CustomerType.LOYAL, CustomerType.VIP, false),

                Arguments.of(CustomerType.VIP, CustomerType.NEW, true),
                Arguments.of(CustomerType.VIP, CustomerType.LOYAL, true),
                Arguments.of(CustomerType.VIP, CustomerType.VIP, false)

        );
    }

    private static Stream<Arguments> getArgumentsForLesserThanCheck() {
        return Stream.of(

                Arguments.of(CustomerType.NEW, CustomerType.NEW, false),
                Arguments.of(CustomerType.NEW, CustomerType.LOYAL, true),
                Arguments.of(CustomerType.NEW, CustomerType.VIP, true),


                Arguments.of(CustomerType.LOYAL, CustomerType.NEW, false),
                Arguments.of(CustomerType.LOYAL, CustomerType.LOYAL, false),
                Arguments.of(CustomerType.LOYAL, CustomerType.VIP, true),

                Arguments.of(CustomerType.VIP, CustomerType.NEW, false),
                Arguments.of(CustomerType.VIP, CustomerType.LOYAL, false),
                Arguments.of(CustomerType.VIP, CustomerType.VIP, false)

        );
    }

    private static Stream<Arguments> getArgumentsForUnderVIPCategoryCheck() {
        return Stream.of(

                Arguments.of(CustomerType.NEW, true),
                Arguments.of(CustomerType.LOYAL, true),
                Arguments.of(CustomerType.VIP, false)
        );
    }

    @DisplayName("CustomerType Greater Than Test")
    @ParameterizedTest(name = "{0} is Greater than {1}, Answer = {2}")
    @MethodSource("getArgumentsForGreaterThanCheck")
    void testIsGreaterThanCheck(CustomerType type1, CustomerType type2, boolean expectedValue) {
        assertEquals(type1.isGreaterThan(type2), expectedValue);
    }

    @DisplayName("CustomerType Lesser Than Test")
    @ParameterizedTest(name = "{0} is Lesser than {1}, Answer = {2}")
    @MethodSource("getArgumentsForLesserThanCheck")
    void testIsLesserThanCheck(CustomerType type1, CustomerType type2, boolean expectedValue) {
        assertEquals(type1.isLesserThan(type2), expectedValue);
    }

    @DisplayName("CustomerType Under VIP Category Test")
    @ParameterizedTest(name = "{0} is Out for Delivery, Answer = {1}")
    @MethodSource("getArgumentsForUnderVIPCategoryCheck")
    void testIsLesserThanCheck(CustomerType type, boolean expectedValue) {
        assertEquals(type.isUnderVIPCategory(), expectedValue);
    }
}