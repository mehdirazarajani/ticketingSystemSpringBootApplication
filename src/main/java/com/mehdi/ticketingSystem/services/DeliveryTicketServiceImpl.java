package com.mehdi.ticketingSystem.services;

import com.mehdi.ticketingSystem.model.DeliveryDetailsList;
import com.mehdi.ticketingSystem.model.DeliveryTicket;
import com.mehdi.ticketingSystem.repositories.interfaces.DeliveryTicketRepository;
import com.mehdi.ticketingSystem.services.interfaces.DeliveryTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DeliveryTicketServiceImpl implements DeliveryTicketService {

    @Autowired
    DeliveryTicketRepository deliveryTicketRepository;

    @Override
    public void deleteAll() {
        deliveryTicketRepository.deleteAll();
    }

    @Override
    public void insertAll(DeliveryDetailsList deliveryDetailsList) {
        deliveryTicketRepository.insertAll(deliveryDetailsList);
    }

    @Override
    public List<DeliveryTicket> getAll() {
        return deliveryTicketRepository.getAll();
    }
}
