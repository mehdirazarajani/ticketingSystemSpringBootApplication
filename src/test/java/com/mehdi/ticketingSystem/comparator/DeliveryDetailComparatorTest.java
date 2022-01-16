package com.mehdi.ticketingSystem.comparator;

import com.mehdi.ticketingSystem.model.CustomerType;
import com.mehdi.ticketingSystem.model.DeliveryDetails;
import com.mehdi.ticketingSystem.utils.ExtTimestamp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeliveryDetailComparatorTest {

    DeliveryDetailComparator comparator = new DeliveryDetailComparator();

    private static Stream<Arguments> getArguments() {
        return Stream.of(

                Arguments.of(
                        new DeliveryDetails().setCustomerType(CustomerType.NEW),
                        Operator.EQUALS,
                        new DeliveryDetails().setCustomerType(CustomerType.LOYAL),
                        "CustomerType.NEW = CustomerType.LOYAL"
                ),
                Arguments.of(
                        new DeliveryDetails().setCustomerType(CustomerType.NEW),
                        Operator.LESSER_THAN,
                        new DeliveryDetails().setCustomerType(CustomerType.VIP),
                        "CustomerType.LOYAL < CustomerType.VIP"
                ),
                Arguments.of(
                        new DeliveryDetails().setCustomerType(CustomerType.VIP),
                        Operator.GREATER_THAN,
                        new DeliveryDetails().setCustomerType(CustomerType.LOYAL),
                        "CustomerType.VIP > CustomerType.LOYAL"
                ),
                Arguments.of(
                        new DeliveryDetails().setExpectedDeliveryTime(new ExtTimestamp(System.currentTimeMillis() + (long) Math.pow(10, 7))),
                        Operator.LESSER_THAN,
                        new DeliveryDetails().setExpectedDeliveryTime(new ExtTimestamp(System.currentTimeMillis() + (long) Math.pow(10, 6))),
                        "Expected Delivery Time: Current time + 10^7 < Current time + 10 ^ 6"
                ),
                Arguments.of(
                        new DeliveryDetails().setExpectedDeliveryTime(new ExtTimestamp(System.currentTimeMillis() - (long) Math.pow(10, 3))),
                        Operator.GREATER_THAN,
                        new DeliveryDetails().setExpectedDeliveryTime(new ExtTimestamp(System.currentTimeMillis() + (long) Math.pow(10, 6))),
                        "Expected Delivery Time: Current time - 10^3 > Current time + 10 ^ 6"
                ),
                Arguments.of(
                        new DeliveryDetails().setTimeToReachDestination(new ExtTimestamp(DeliveryDetails.MEAN_TIME_TO_PREPARE_FOOD + (long) Math.pow(10, 3))),
                        Operator.LESSER_THAN,
                        new DeliveryDetails().setTimeToReachDestination(new ExtTimestamp(DeliveryDetails.MEAN_TIME_TO_PREPARE_FOOD + (long) Math.pow(10, 5))),
                        "Estimated Delivery Time: Mean Time + 10^3 < Mean Time + 10 ^ 5"
                ),
                Arguments.of(
                        new DeliveryDetails().setTimeToReachDestination(new ExtTimestamp(DeliveryDetails.MEAN_TIME_TO_PREPARE_FOOD + (long) Math.pow(10, 3))),
                        Operator.GREATER_THAN,
                        new DeliveryDetails().setTimeToReachDestination(new ExtTimestamp(DeliveryDetails.MEAN_TIME_TO_PREPARE_FOOD - (long) Math.pow(10, 5))),
                        "Estimated Delivery Time: Mean Time + 10^3 > Mean Time - 10 ^ 5"
                ),
                Arguments.of(
                        new DeliveryDetails().setDeliveryId(1),
                        Operator.GREATER_THAN,
                        new DeliveryDetails().setDeliveryId(2),
                        "Delivery Id: 1 > 2"
                ),
                Arguments.of(
                        new DeliveryDetails().setDeliveryId(10),
                        Operator.LESSER_THAN,
                        new DeliveryDetails().setDeliveryId(2),
                        "Delivery Id: 10 < 2"
                )

        );
    }

    @DisplayName("CustomerType Greater Than Test")
    @ParameterizedTest(name = "{3}")
    @MethodSource("getArguments")
    void testIsGreaterThanCheck(DeliveryDetails detail1, Operator operator, DeliveryDetails detail2, String title) {
        int value = comparator.compare(detail1, detail2);
        switch (operator) {
            case EQUALS:
                assertEquals(value, 0);
                break;
            case LESSER_THAN:
                assertTrue(value < 0);
                break;
            case GREATER_THAN:
                assertTrue(value > 0);
                break;
        }
    }

    enum Operator {
        GREATER_THAN,
        LESSER_THAN,
        EQUALS
    }


}