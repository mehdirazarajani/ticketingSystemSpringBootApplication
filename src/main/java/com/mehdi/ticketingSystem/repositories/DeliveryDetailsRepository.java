package com.mehdi.ticketingSystem.repositories;

import com.mehdi.ticketingSystem.domain.DeliveryDetails;

import java.util.List;

public interface DeliveryDetailsRepository {

    List<DeliveryDetails> getAllUncompletedDeliveryDetails();

}
