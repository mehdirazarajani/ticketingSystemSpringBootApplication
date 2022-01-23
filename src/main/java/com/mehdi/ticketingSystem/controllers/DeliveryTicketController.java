package com.mehdi.ticketingSystem.controllers;

import com.mehdi.ticketingSystem.model.DeliveryDetailsList;
import com.mehdi.ticketingSystem.model.DeliveryTicket;
import com.mehdi.ticketingSystem.model.response.common.SuccessResponse;
import com.mehdi.ticketingSystem.services.interfaces.DeliveryDetailsService;
import com.mehdi.ticketingSystem.services.interfaces.DeliveryTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/delivery-ticket")
public class DeliveryTicketController {

    @Autowired
    DeliveryDetailsService deliveryDetailsService;
    @Autowired
    DeliveryTicketService deliveryTicketService;

    @PostMapping("/populate")
    public ResponseEntity<SuccessResponse<DeliveryDetailsList>> createDeliveryTickets() {
        // delete all the tickets created
        deliveryTicketService.deleteAll();

        // calculate the freshly created values
        DeliveryDetailsList list = deliveryDetailsService.getAllUncompletedDeliveryDetails();

        // add all the new values
        deliveryTicketService.insertAll(list);

        return new ResponseEntity<>(
                new SuccessResponse<>("All tickets are created", list),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<SuccessResponse<List<DeliveryTicket>>> getAll(){
        return new ResponseEntity<>(
                new SuccessResponse<>("", deliveryTicketService.getAll()),
                HttpStatus.OK
        );
    }

}
