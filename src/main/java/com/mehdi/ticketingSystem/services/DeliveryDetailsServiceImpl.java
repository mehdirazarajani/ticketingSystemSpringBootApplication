package com.mehdi.ticketingSystem.services;

import com.mehdi.ticketingSystem.model.DeliveryDetails;
import com.mehdi.ticketingSystem.repositories.DeliveryDetailsRepository;
import com.mehdi.ticketingSystem.services.interfaces.DeliveryDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DeliveryDetailsServiceImpl implements DeliveryDetailsService {

    @Autowired
    DeliveryDetailsRepository deliveryDetailsRepository;

    @Override
    public List<DeliveryDetails> getAllUncompletedDeliveryDetails() {
        return deliveryDetailsRepository.getAllUncompletedDeliveryDetails();
    }
}
