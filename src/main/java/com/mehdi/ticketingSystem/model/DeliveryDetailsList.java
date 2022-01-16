package com.mehdi.ticketingSystem.model;

import com.mehdi.ticketingSystem.comparator.DeliveryDetailComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class DeliveryDetailsList extends ArrayList<DeliveryDetails> {

    private final Comparator<DeliveryDetails> comparator;
    public DeliveryDetailsList(Comparator<DeliveryDetails> comparator) {
        this.comparator = comparator;
    }

    public DeliveryDetailsList(Collection<? extends DeliveryDetails> c, Comparator<DeliveryDetails> comparator) {
        super(c);
        this.comparator = comparator;
    }

    public DeliveryDetailsList(Collection<? extends DeliveryDetails> c) {
        super(c);
        this.comparator = new DeliveryDetailComparator();
    }

    public void getDeliveryInPriority(){
        this.sort(comparator);
        Collections.reverse(this);
    }

}
