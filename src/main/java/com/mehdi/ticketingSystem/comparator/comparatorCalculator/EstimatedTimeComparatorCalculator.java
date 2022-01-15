package com.mehdi.ticketingSystem.comparator.comparatorCalculator;

import com.mehdi.ticketingSystem.model.DeliveryDetails;

public class EstimatedTimeComparatorCalculator extends ComparatorCalculator {
    public EstimatedTimeComparatorCalculator(int priority) {
        super(priority);
    }

    @Override
    public int calculate(DeliveryDetails delivery1, DeliveryDetails delivery2) {
        int minutesLateForDelivery1 = 0;
        int minutesLateForDelivery2 = 0;

        if (delivery1.estimatedTimeCanExceed().isPresent())
            minutesLateForDelivery1 = delivery1.estimatedTimeCanExceed().get();
        if (delivery2.estimatedTimeCanExceed().isPresent())
            minutesLateForDelivery2 = delivery2.estimatedTimeCanExceed().get();

        return (minutesLateForDelivery1 - minutesLateForDelivery2) * priority;
    }
}
