package com.mehdi.ticketingSystem.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeliveryStatusTest {

    private static Stream<Arguments> getArgumentsForGreaterThanCheck() {
        return Stream.of(

                Arguments.of(DeliveryStatus.ORDER_RECEIVED, DeliveryStatus.ORDER_RECEIVED, false),
                Arguments.of(DeliveryStatus.ORDER_RECEIVED, DeliveryStatus.ORDER_PREPARING, false),
                Arguments.of(DeliveryStatus.ORDER_RECEIVED, DeliveryStatus.ORDER_PICKED, false),
                Arguments.of(DeliveryStatus.ORDER_RECEIVED, DeliveryStatus.ORDER_DELIVERED, false),

                Arguments.of(DeliveryStatus.ORDER_PREPARING, DeliveryStatus.ORDER_RECEIVED, true),
                Arguments.of(DeliveryStatus.ORDER_PREPARING, DeliveryStatus.ORDER_PREPARING, false),
                Arguments.of(DeliveryStatus.ORDER_PREPARING, DeliveryStatus.ORDER_PICKED, false),
                Arguments.of(DeliveryStatus.ORDER_PREPARING, DeliveryStatus.ORDER_DELIVERED, false),

                Arguments.of(DeliveryStatus.ORDER_PICKED, DeliveryStatus.ORDER_RECEIVED, true),
                Arguments.of(DeliveryStatus.ORDER_PICKED, DeliveryStatus.ORDER_PREPARING, true),
                Arguments.of(DeliveryStatus.ORDER_PICKED, DeliveryStatus.ORDER_PICKED, false),
                Arguments.of(DeliveryStatus.ORDER_PICKED, DeliveryStatus.ORDER_DELIVERED, false),

                Arguments.of(DeliveryStatus.ORDER_DELIVERED, DeliveryStatus.ORDER_RECEIVED, true),
                Arguments.of(DeliveryStatus.ORDER_DELIVERED, DeliveryStatus.ORDER_PREPARING, true),
                Arguments.of(DeliveryStatus.ORDER_DELIVERED, DeliveryStatus.ORDER_PICKED, true),
                Arguments.of(DeliveryStatus.ORDER_DELIVERED, DeliveryStatus.ORDER_DELIVERED, false)

        );
    }

    private static Stream<Arguments> getArgumentsForLesserThanCheck() {
        return Stream.of(

                Arguments.of(DeliveryStatus.ORDER_RECEIVED, DeliveryStatus.ORDER_RECEIVED, false),
                Arguments.of(DeliveryStatus.ORDER_RECEIVED, DeliveryStatus.ORDER_PREPARING, true),
                Arguments.of(DeliveryStatus.ORDER_RECEIVED, DeliveryStatus.ORDER_PICKED, true),
                Arguments.of(DeliveryStatus.ORDER_RECEIVED, DeliveryStatus.ORDER_DELIVERED, true),

                Arguments.of(DeliveryStatus.ORDER_PREPARING, DeliveryStatus.ORDER_RECEIVED, false),
                Arguments.of(DeliveryStatus.ORDER_PREPARING, DeliveryStatus.ORDER_PREPARING, false),
                Arguments.of(DeliveryStatus.ORDER_PREPARING, DeliveryStatus.ORDER_PICKED, true),
                Arguments.of(DeliveryStatus.ORDER_PREPARING, DeliveryStatus.ORDER_DELIVERED, true),

                Arguments.of(DeliveryStatus.ORDER_PICKED, DeliveryStatus.ORDER_RECEIVED, false),
                Arguments.of(DeliveryStatus.ORDER_PICKED, DeliveryStatus.ORDER_PREPARING, false),
                Arguments.of(DeliveryStatus.ORDER_PICKED, DeliveryStatus.ORDER_PICKED, false),
                Arguments.of(DeliveryStatus.ORDER_PICKED, DeliveryStatus.ORDER_DELIVERED, true),

                Arguments.of(DeliveryStatus.ORDER_DELIVERED, DeliveryStatus.ORDER_RECEIVED, false),
                Arguments.of(DeliveryStatus.ORDER_DELIVERED, DeliveryStatus.ORDER_PREPARING, false),
                Arguments.of(DeliveryStatus.ORDER_DELIVERED, DeliveryStatus.ORDER_PICKED, false),
                Arguments.of(DeliveryStatus.ORDER_DELIVERED, DeliveryStatus.ORDER_DELIVERED, false)

        );
    }

    private static Stream<Arguments> getArgumentsForOutForDeliveryCheck() {
        return Stream.of(

                Arguments.of(DeliveryStatus.ORDER_RECEIVED, false),
                Arguments.of(DeliveryStatus.ORDER_PREPARING, false),
                Arguments.of(DeliveryStatus.ORDER_PICKED, true),
                Arguments.of(DeliveryStatus.ORDER_DELIVERED, true)

        );
    }

    @DisplayName("DeliveryStatus Greater Than Test")
    @ParameterizedTest(name = "{0} is Greater than {1}, Answer = {2}")
    @MethodSource("getArgumentsForGreaterThanCheck")
    void testIsGreaterThanCheck(DeliveryStatus status1, DeliveryStatus status2, boolean expectedValue) {
        assertEquals(status1.isGreaterThan(status2), expectedValue);
    }

    @DisplayName("DeliveryStatus Lesser Than Test")
    @ParameterizedTest(name = "{0} is Lesser than {1}, Answer = {2}")
    @MethodSource("getArgumentsForLesserThanCheck")
    void testIsLesserThanCheck(DeliveryStatus status1, DeliveryStatus status2, boolean expectedValue) {
        assertEquals(status1.isLesserThan(status2), expectedValue);
    }

    @DisplayName("DeliveryStatus Is Out For Delivery Test")
    @ParameterizedTest(name = "{0} is Out for Delivery, Answer = {1}")
    @MethodSource("getArgumentsForOutForDeliveryCheck")
    void testIsLesserThanCheck(DeliveryStatus status, boolean expectedValue) {
        assertEquals(status.isOutForDelivery(), expectedValue);
    }


}