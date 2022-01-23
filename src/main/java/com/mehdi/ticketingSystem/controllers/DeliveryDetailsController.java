package com.mehdi.ticketingSystem.controllers;

import com.mehdi.ticketingSystem.model.DeliveryDetailsList;
import com.mehdi.ticketingSystem.model.dtos.InsertDeliveryDetailsDTO;
import com.mehdi.ticketingSystem.model.response.common.SuccessResponse;
import com.mehdi.ticketingSystem.services.interfaces.DeliveryDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/delivery-details")
public class DeliveryDetailsController {

    @Autowired
    DeliveryDetailsService deliveryDetailsService;

    @GetMapping("/uncompleted-details-in-priority")
    public ResponseEntity<SuccessResponse<DeliveryDetailsList>> uncompletedDetailsInPriority() {
        DeliveryDetailsList list = deliveryDetailsService.getAllUncompletedDeliveryDetails();
        return new ResponseEntity<>(
                new SuccessResponse<>("this is list in High to Priority level", list),
                HttpStatus.OK
        );
    }

    @PostMapping("/populate")
    public ResponseEntity<SuccessResponse<DeliveryDetailsList>> createDeliveryTickets() {
        DeliveryDetailsList list = deliveryDetailsService.getAllUncompletedDeliveryDetails();
        return new ResponseEntity<>(
                new SuccessResponse<>("this is list in High to Priority level", list),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<SuccessResponse<Object>> delete() {
        deliveryDetailsService.deleteAll();
        return new ResponseEntity<>(
                new SuccessResponse<>("Table is empty now", null),
                HttpStatus.OK
        );
    }

    @PostMapping("/insert")
    public ResponseEntity<SuccessResponse<Object>> insert(@Valid @RequestBody InsertDeliveryDetailsDTO insertDeliveryDetailsDTO){
        deliveryDetailsService.addDeliveryDetails(
                insertDeliveryDetailsDTO.getDeliveryId(),
                insertDeliveryDetailsDTO.getCustomerType(),
                insertDeliveryDetailsDTO.getDeliveryStatus(),
                insertDeliveryDetailsDTO.getExpectedDeliveryTime(),
                insertDeliveryDetailsDTO.getCurrentDistanceFromDestinationInMeters(),
                insertDeliveryDetailsDTO.getTimeToReachDestination()
        );
        return new ResponseEntity<>(
                new SuccessResponse<>("delivery details inserted successfully", null),
                HttpStatus.OK
        );
    }

}
