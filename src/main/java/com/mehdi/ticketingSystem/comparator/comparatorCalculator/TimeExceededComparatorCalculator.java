package com.mehdi.ticketingSystem.comparator.comparatorCalculator;

import com.mehdi.ticketingSystem.model.DeliveryDetails;

public class TimeExceededComparatorCalculator extends ComparatorCalculator {

    public TimeExceededComparatorCalculator(int priority) {
        super(priority);
    }

    @Override
    public int calculate(DeliveryDetails delivery1, DeliveryDetails delivery2) {
        int minutesLateForDelivery1 = 0;
        int minutesLateForDelivery2 = 0;

        if (delivery1.timeExceeded().isPresent())
            minutesLateForDelivery1 = delivery1.timeExceeded().get();
        if (delivery2.timeExceeded().isPresent())
            minutesLateForDelivery2 = delivery2.timeExceeded().get();

        return (minutesLateForDelivery2 - minutesLateForDelivery1) * priority;
    }
}
