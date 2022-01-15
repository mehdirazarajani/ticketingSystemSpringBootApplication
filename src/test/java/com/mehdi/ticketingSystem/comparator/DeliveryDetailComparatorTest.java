package com.mehdi.ticketingSystem.comparator;

import com.mehdi.ticketingSystem.domain.CustomerType;
import com.mehdi.ticketingSystem.domain.DeliveryDetails;
import com.mehdi.ticketingSystem.domain.DeliveryStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.Timestamp;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeliveryDetailComparatorTest {

    DeliveryDetailComparator comparator = new DeliveryDetailComparator();

    private static Stream<Arguments> getArguments() {
        return Stream.of(

                Arguments.of(
                        new DeliveryDetailsBuilder().customerType(CustomerType.NEW).build(),
                        Operator.EQUALS,
                        new DeliveryDetailsBuilder().customerType(CustomerType.LOYAL).build(),
                        "CustomerType.NEW = CustomerType.LOYAL"
                ),
                Arguments.of(
                        new DeliveryDetailsBuilder().customerType(CustomerType.NEW).build(),
                        Operator.LESSER_THAN,
                        new DeliveryDetailsBuilder().customerType(CustomerType.VIP).build(),
                        "CustomerType.LOYAL < CustomerType.VIP"
                ),
                Arguments.of(
                        new DeliveryDetailsBuilder().customerType(CustomerType.VIP).build(),
                        Operator.GREATER_THAN,
                        new DeliveryDetailsBuilder().customerType(CustomerType.LOYAL).build(),
                        "CustomerType.VIP > CustomerType.LOYAL"
                ),
                Arguments.of(
                        new DeliveryDetailsBuilder().expectedDeliveryTime(System.currentTimeMillis() + (long) Math.pow(10, 7)).build(),
                        Operator.LESSER_THAN,
                        new DeliveryDetailsBuilder().expectedDeliveryTime(System.currentTimeMillis() + (long) Math.pow(10, 6)).build(),
                        "Expected Delivery Time: Current time + 10^7 < Current time + 10 ^ 6"
                ),
                Arguments.of(
                        new DeliveryDetailsBuilder().expectedDeliveryTime(System.currentTimeMillis() - (long) Math.pow(10, 3)).build(),
                        Operator.GREATER_THAN,
                        new DeliveryDetailsBuilder().expectedDeliveryTime(System.currentTimeMillis() + (long) Math.pow(10, 6)).build(),
                        "Expected Delivery Time: Current time - 10^3 > Current time + 10 ^ 6"
                ),
                Arguments.of(
                        new DeliveryDetailsBuilder().timeToReachDestination(DeliveryDetails.MEAN_TIME_TO_PREPARE_FOOD + (long) Math.pow(10, 3)).build(),
                        Operator.LESSER_THAN,
                        new DeliveryDetailsBuilder().timeToReachDestination(DeliveryDetails.MEAN_TIME_TO_PREPARE_FOOD + (long) Math.pow(10, 5)).build(),
                        "Estimated Delivery Time: Mean Time + 10^3 < Mean Time + 10 ^ 5"
                ),
                Arguments.of(
                        new DeliveryDetailsBuilder().timeToReachDestination(DeliveryDetails.MEAN_TIME_TO_PREPARE_FOOD + (long) Math.pow(10, 3)).build(),
                        Operator.GREATER_THAN,
                        new DeliveryDetailsBuilder().timeToReachDestination(DeliveryDetails.MEAN_TIME_TO_PREPARE_FOOD - (long) Math.pow(10, 5)).build(),
                        "Estimated Delivery Time: Mean Time + 10^3 > Mean Time - 10 ^ 5"
                ),
                Arguments.of(
                        new DeliveryDetailsBuilder().deliveryId(1).build(),
                        Operator.GREATER_THAN,
                        new DeliveryDetailsBuilder().deliveryId(2).build(),
                        "Delivery Id: 1 > 2"
                ),
                Arguments.of(
                        new DeliveryDetailsBuilder().deliveryId(10).build(),
                        Operator.LESSER_THAN,
                        new DeliveryDetailsBuilder().deliveryId(2).build(),
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

    static class DeliveryDetailsBuilder {

        private final DeliveryDetails deliveryDetails = new DeliveryDetails(0, CustomerType.VIP, DeliveryStatus.ORDER_PREPARING, new Timestamp(0), 0, new Timestamp(0));

        DeliveryDetails build() {
            return deliveryDetails;
        }

        DeliveryDetailsBuilder deliveryId(int deliveryId) {
            deliveryDetails.setDeliveryId(deliveryId);
            return this;
        }

        DeliveryDetailsBuilder customerType(CustomerType customerType) {
            deliveryDetails.setCustomerType(customerType);
            return this;
        }

        DeliveryDetailsBuilder deliveryStatus(DeliveryStatus deliveryStatus) {
            deliveryDetails.setDeliveryStatus(deliveryStatus);
            return this;
        }

        DeliveryDetailsBuilder expectedDeliveryTime(long expectedDeliveryTime) {
            deliveryDetails.setExpectedDeliveryTime(new Timestamp(expectedDeliveryTime));
            return this;
        }

        DeliveryDetailsBuilder currentDistanceFromDestinationInMeters(int currentDistanceFromDestinationInMeters) {
            deliveryDetails.setCurrentDistanceFromDestinationInMeters(currentDistanceFromDestinationInMeters);
            return this;
        }

        DeliveryDetailsBuilder timeToReachDestination(long timeToReachDestination) {
            deliveryDetails.setTimeToReachDestination(new Timestamp(timeToReachDestination));
            return this;
        }

    }


}