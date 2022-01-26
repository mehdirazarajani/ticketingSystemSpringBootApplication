package com.mehdi.ticketingSystem.services.interfaces;

import com.mehdi.ticketingSystem.model.DeliveryTickets;

public interface DeliveryTicketService {

    DeliveryTickets populate();

    DeliveryTickets getAll();
}
