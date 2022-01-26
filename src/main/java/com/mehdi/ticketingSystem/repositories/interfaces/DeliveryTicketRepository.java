package com.mehdi.ticketingSystem.repositories.interfaces;

import com.mehdi.ticketingSystem.model.DeliveryDetailsList;
import com.mehdi.ticketingSystem.model.DeliveryTickets;

public interface DeliveryTicketRepository {

    void deleteAll();

    void insertAll(DeliveryDetailsList deliveryDetailsList);

    DeliveryTickets getAll();

}
