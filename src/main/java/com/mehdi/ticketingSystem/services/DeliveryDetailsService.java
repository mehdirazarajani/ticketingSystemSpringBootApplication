package com.mehdi.ticketingSystem.services;

import com.mehdi.ticketingSystem.domain.DeliveryDetails;

import java.util.List;

public interface DeliveryDetailsService {

    List<DeliveryDetails> getAllUncompletedDeliveryDetails();

}
