package com.mehdi.ticketingSystem.controllers;

import com.mehdi.ticketingSystem.model.DeliveryTickets;
import com.mehdi.ticketingSystem.model.response.common.SuccessResponse;
import com.mehdi.ticketingSystem.services.interfaces.DeliveryTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/delivery-ticket")
public class DeliveryTicketController {

    @Autowired
    DeliveryTicketService deliveryTicketService;

    @PostMapping("/populate")
    public ResponseEntity<SuccessResponse<DeliveryTickets>> createDeliveryTickets() {
        return new ResponseEntity<>(
                new SuccessResponse<>("All tickets are created", deliveryTicketService.populate()),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<SuccessResponse<DeliveryTickets>> getAll(){
        return new ResponseEntity<>(
                new SuccessResponse<>("", deliveryTicketService.getAll()),
                HttpStatus.OK
        );
    }

}
