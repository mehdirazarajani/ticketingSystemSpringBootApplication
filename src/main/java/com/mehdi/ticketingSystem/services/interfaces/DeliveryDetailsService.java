package com.mehdi.ticketingSystem.services.interfaces;

import com.mehdi.ticketingSystem.model.CustomerType;
import com.mehdi.ticketingSystem.model.DeliveryDetailsList;
import com.mehdi.ticketingSystem.model.DeliveryStatus;

public interface DeliveryDetailsService {

    DeliveryDetailsList getAllUncompletedDeliveryDetails();

    boolean addDeliveryDetails(int deliveryId, CustomerType customerType, DeliveryStatus deliveryStatus, long expectedDeliveryTime, int currentDistanceFromDestinationInMeters, long timeToReachDestination);

    boolean deleteAll();
}
