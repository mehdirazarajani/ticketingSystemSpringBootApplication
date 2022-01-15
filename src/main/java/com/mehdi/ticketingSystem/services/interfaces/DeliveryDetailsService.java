package com.mehdi.ticketingSystem.services.interfaces;

import com.mehdi.ticketingSystem.model.DeliveryDetails;

import java.util.List;

public interface DeliveryDetailsService {

    List<DeliveryDetails> getAllUncompletedDeliveryDetails();

}
