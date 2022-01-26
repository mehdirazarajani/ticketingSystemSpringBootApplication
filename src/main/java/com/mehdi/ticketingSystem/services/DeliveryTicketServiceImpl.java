package com.mehdi.ticketingSystem.services;

import com.mehdi.ticketingSystem.model.DeliveryDetailsList;
import com.mehdi.ticketingSystem.model.DeliveryTicket;
import com.mehdi.ticketingSystem.model.DeliveryTickets;
import com.mehdi.ticketingSystem.repositories.interfaces.DeliveryDetailsRepository;
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
    @Autowired
    DeliveryDetailsRepository deliveryDetailsRepository;

    @Override
    public DeliveryTickets populate() {
        // delete all the tickets created
        deliveryTicketRepository.deleteAll();

        // calculate the freshly created values
        DeliveryDetailsList list = deliveryDetailsRepository.getAllUncompletedDeliveryDetails();

        // add all the new values
        deliveryTicketRepository.insertAll(list);

        return deliveryTicketRepository.getAll();
    }

    @Override
    public DeliveryTickets getAll() {
        return deliveryTicketRepository.getAll();
    }
}
