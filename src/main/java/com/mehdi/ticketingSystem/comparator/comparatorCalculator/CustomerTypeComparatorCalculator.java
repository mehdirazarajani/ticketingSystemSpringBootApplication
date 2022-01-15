package com.mehdi.ticketingSystem.comparator.comparatorCalculator;

import com.mehdi.ticketingSystem.model.DeliveryDetails;
import org.springframework.stereotype.Component;

public class CustomerTypeComparatorCalculator extends ComparatorCalculator {

    public CustomerTypeComparatorCalculator(int priority) {
        super(priority);
    }

    @Override
    public int calculate(DeliveryDetails delivery1, DeliveryDetails delivery2) {
        return (delivery1.getCustomerType().level() - delivery2.getCustomerType().level()) * priority;
    }
}
