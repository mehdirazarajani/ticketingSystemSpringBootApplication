package com.mehdi.ticketingSystem.comparator.comparatorCalculator;

import com.mehdi.ticketingSystem.model.DeliveryDetails;

public abstract class ComparatorCalculator {

    ComparatorCalculator next;
    protected final int priority;

    public ComparatorCalculator(int priority){
        this.priority = priority;
    }

    public ComparatorCalculator moveNext(){
        return next;
    }

    abstract public int calculate(DeliveryDetails delivery1, DeliveryDetails delivery2);
}
