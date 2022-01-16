package com.mehdi.ticketingSystem.services;

import com.mehdi.ticketingSystem.model.CustomerType;
import com.mehdi.ticketingSystem.model.DeliveryDetailsList;
import com.mehdi.ticketingSystem.model.DeliveryStatus;
import com.mehdi.ticketingSystem.repositories.interfaces.DeliveryDetailsRepository;
import com.mehdi.ticketingSystem.services.interfaces.DeliveryDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeliveryDetailsServiceImpl implements DeliveryDetailsService {

    @Autowired
    DeliveryDetailsRepository deliveryDetailsRepository;

    @Override
    public DeliveryDetailsList getAllUncompletedDeliveryDetails() {
        return deliveryDetailsRepository.getAllUncompletedDeliveryDetails();
    }

    @Override
    public boolean addDeliveryDetails(int deliveryId, CustomerType customerType, DeliveryStatus deliveryStatus, long expectedDeliveryTime, int currentDistanceFromDestinationInMeters, long timeToReachDestination) {
        return deliveryDetailsRepository.create(deliveryId, customerType, deliveryStatus, expectedDeliveryTime, currentDistanceFromDestinationInMeters, timeToReachDestination);
    }

    @Override
    public boolean deleteAll() {
        return deliveryDetailsRepository.deleteAll();
    }
}
