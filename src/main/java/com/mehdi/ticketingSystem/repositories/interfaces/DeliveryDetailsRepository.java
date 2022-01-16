package com.mehdi.ticketingSystem.repositories.interfaces;

import com.mehdi.ticketingSystem.model.CustomerType;
import com.mehdi.ticketingSystem.model.DeliveryDetailsList;
import com.mehdi.ticketingSystem.model.DeliveryStatus;

public interface DeliveryDetailsRepository {

    DeliveryDetailsList getAllUncompletedDeliveryDetails();

    boolean create(int deliveryId, CustomerType customerType, DeliveryStatus deliveryStatus, long expectedDeliveryTime, int currentDistanceFromDestinationInMeters, long timeToReachDestination);

    boolean deleteAll();
}
