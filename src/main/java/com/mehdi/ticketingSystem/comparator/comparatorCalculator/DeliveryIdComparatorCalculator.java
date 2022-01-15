package com.mehdi.ticketingSystem.comparator.comparatorCalculator;

import com.mehdi.ticketingSystem.model.DeliveryDetails;

public class DeliveryIdComparatorCalculator extends ComparatorCalculator {

    public DeliveryIdComparatorCalculator(int priority) {
        super(priority);
    }

    @Override
    public int calculate(DeliveryDetails delivery1, DeliveryDetails delivery2) {
        return (delivery2.getDeliveryId() - delivery1.getDeliveryId())
                * priority;
    }
}
