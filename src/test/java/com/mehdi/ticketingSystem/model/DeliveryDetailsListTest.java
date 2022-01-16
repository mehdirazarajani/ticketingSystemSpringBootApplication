package com.mehdi.ticketingSystem.model;

import com.mehdi.ticketingSystem.utils.ExtTimestamp;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class DeliveryDetailsListTest {

    @Test
    void testCompleteData() {
        long baseTimestamp = System.currentTimeMillis() + DeliveryDetails.MEAN_TIME_TO_PREPARE_FOOD + minutes(30);

        /*
            int deliveryId;
            CustomerType customerType;
            DeliveryStatus deliveryStatus;
            ExtTimestamp expectedDeliveryTime;
            int currentDistanceFromDestinationInMeters;
            ExtTimestamp timeToReachDestination;
         */
        DeliveryDetailsList deliveryDetailsList = new DeliveryDetailsList(new ArrayList<>(List.of(
                new DeliveryDetails(1, CustomerType.NEW, DeliveryStatus.ORDER_PREPARING, new ExtTimestamp(baseTimestamp + minutes(30)), 100, new ExtTimestamp(baseTimestamp + minutes(30))),
                new DeliveryDetails(2, CustomerType.LOYAL, DeliveryStatus.ORDER_PREPARING, new ExtTimestamp(baseTimestamp + minutes(10)), 100, new ExtTimestamp(baseTimestamp + minutes(50))),
                new DeliveryDetails(3, CustomerType.VIP, DeliveryStatus.ORDER_RECEIVED, new ExtTimestamp(baseTimestamp + minutes(10)), 100, new ExtTimestamp(baseTimestamp + minutes(50))),
                new DeliveryDetails(4, CustomerType.NEW, DeliveryStatus.ORDER_RECEIVED, new ExtTimestamp(baseTimestamp + minutes(100)), 100, new ExtTimestamp(baseTimestamp + minutes(30))),
                new DeliveryDetails(5, CustomerType.LOYAL, DeliveryStatus.ORDER_RECEIVED, new ExtTimestamp(baseTimestamp - minutes(100)), 100, new ExtTimestamp(baseTimestamp + minutes(30))),
                new DeliveryDetails(6, CustomerType.VIP, DeliveryStatus.ORDER_RECEIVED, new ExtTimestamp(baseTimestamp - minutes(100)), 100, new ExtTimestamp(baseTimestamp + minutes(30))),
                new DeliveryDetails(7, CustomerType.VIP, DeliveryStatus.ORDER_RECEIVED, new ExtTimestamp(baseTimestamp - minutes(100)), 100, new ExtTimestamp(baseTimestamp + minutes(30))),
                new DeliveryDetails(8, CustomerType.LOYAL, DeliveryStatus.ORDER_RECEIVED, new ExtTimestamp(baseTimestamp + minutes(100)), 100, new ExtTimestamp(baseTimestamp + minutes(50))),
                new DeliveryDetails(9, CustomerType.LOYAL, DeliveryStatus.ORDER_RECEIVED, new ExtTimestamp(baseTimestamp + minutes(100)), 100, new ExtTimestamp(baseTimestamp + minutes(50))),
                new DeliveryDetails(10, CustomerType.LOYAL, DeliveryStatus.ORDER_PREPARING, new ExtTimestamp(baseTimestamp + minutes(1000)), 100, new ExtTimestamp(baseTimestamp + minutes(60)))
        )));

        deliveryDetailsList.getDeliveryInPriority();
        List<Integer> expectedList = List.of(6, 7, 5, 3, 2, 1, 4, 8, 9, 10);
        List<Integer> actualList = deliveryDetailsList.stream().map(DeliveryDetails::getDeliveryId).collect(Collectors.toList());

        assertThat(expectedList).isEqualTo(actualList);
    }

    private long minutes(int min) {
        return (long) min * 1000 * 60;
    }

}