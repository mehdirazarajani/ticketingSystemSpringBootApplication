package com.mehdi.ticketingSystem.repositories.interfaces;

import com.mehdi.ticketingSystem.model.DeliveryDetailsList;
import com.mehdi.ticketingSystem.model.DeliveryTicket;

import java.util.List;

public interface DeliveryTicketRepository {

    void deleteAll();

    void insertAll(DeliveryDetailsList deliveryDetailsList);

    List<DeliveryTicket> getAll();

}
