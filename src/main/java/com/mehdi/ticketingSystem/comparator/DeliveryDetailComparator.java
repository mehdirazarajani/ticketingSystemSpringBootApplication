package com.mehdi.ticketingSystem.comparator;

import com.mehdi.ticketingSystem.domain.DeliveryDetails;

import java.util.Comparator;
import java.util.Map;

public class DeliveryDetailComparator implements Comparator<DeliveryDetails> {

    final Map<String, Integer> priorities = Map.of(
            DeliveryDetails.CURRENT_DISTANCE_FROM_DESTINATION_IN_METERS, 0,
            DeliveryDetails.TIME_TO_REACH_DESTINATION, 0,
            DeliveryDetails.DELIVERY_STATUS, 0,
            DeliveryDetails.DELIVERY_ID, 10,
            DeliveryDetails.ESTIMATED_TIME, 100,
            DeliveryDetails.EXPECTED_DELIVERY_TIME, 1000,
            DeliveryDetails.CUSTOMER_TYPE, 10000
    );

    @Override
    public int compare(DeliveryDetails delivery1, DeliveryDetails delivery2) {
        int value = 0;

        value += (delivery2.getDeliveryId() - delivery1.getDeliveryId())
                * priorities.get(DeliveryDetails.DELIVERY_ID);

        value += (delivery1.getCustomerType().level() - delivery2.getCustomerType().level())
                * priorities.get(DeliveryDetails.CUSTOMER_TYPE);

        value += handleValueForDeliveryStatus(delivery1, delivery2);

        value += (delivery1.getCurrentDistanceFromDestinationInMeters() - delivery2.getCurrentDistanceFromDestinationInMeters())
                * priorities.get(DeliveryDetails.CURRENT_DISTANCE_FROM_DESTINATION_IN_METERS);

        value += (delivery1.getTimeToReachDestination().absoluteDifferenceInMinutes(delivery2.getTimeToReachDestination()))
                * priorities.get(DeliveryDetails.TIME_TO_REACH_DESTINATION);

        value += handleValueForTimeExceeded(delivery1, delivery2);

        value += handleValueForEstimatedTimeCanExceed(delivery1, delivery2);

        return value;
    }

    int handleValueForDeliveryStatus(DeliveryDetails delivery1, DeliveryDetails delivery2) {
        if (delivery1.getDeliveryStatus().isOutForDelivery() && !delivery2.getDeliveryStatus().isOutForDelivery())
            return priorities.get(DeliveryDetails.DELIVERY_STATUS);
        if (!delivery1.getDeliveryStatus().isOutForDelivery() && delivery2.getDeliveryStatus().isOutForDelivery())
            return -1 * priorities.get(DeliveryDetails.DELIVERY_STATUS);
        return 0;
    }

    int handleValueForTimeExceeded(DeliveryDetails delivery1, DeliveryDetails delivery2) {
        int minutesLateForDelivery1 = 0;
        int minutesLateForDelivery2 = 0;

        if (delivery1.timeExceeded().isPresent())
            minutesLateForDelivery1 = delivery1.timeExceeded().get();
        if (delivery2.timeExceeded().isPresent())
            minutesLateForDelivery2 = delivery2.timeExceeded().get();

        return (minutesLateForDelivery2 - minutesLateForDelivery1)
                * priorities.get(DeliveryDetails.EXPECTED_DELIVERY_TIME);
    }

    int handleValueForEstimatedTimeCanExceed(DeliveryDetails delivery1, DeliveryDetails delivery2) {
        int minutesLateForDelivery1 = 0;
        int minutesLateForDelivery2 = 0;

        if (delivery1.estimatedTimeCanExceed().isPresent())
            minutesLateForDelivery1 = delivery1.estimatedTimeCanExceed().get();
        if (delivery2.estimatedTimeCanExceed().isPresent())
            minutesLateForDelivery2 = delivery2.estimatedTimeCanExceed().get();

        return (minutesLateForDelivery1 - minutesLateForDelivery2)
                * priorities.get(DeliveryDetails.ESTIMATED_TIME);
    }

}
