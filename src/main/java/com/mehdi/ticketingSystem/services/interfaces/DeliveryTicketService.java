package com.mehdi.ticketingSystem.services.interfaces;

import com.mehdi.ticketingSystem.model.DeliveryDetailsList;
import com.mehdi.ticketingSystem.model.DeliveryTicket;

import java.util.List;

public interface DeliveryTicketService {

    void deleteAll();

    void insertAll(DeliveryDetailsList deliveryDetailsList);

    List<DeliveryTicket> getAll();
}
