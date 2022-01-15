package com.mehdi.ticketingSystem.resources;

import com.mehdi.ticketingSystem.commonResponses.SuccessResponse;
import com.mehdi.ticketingSystem.domain.DeliveryDetails;
import com.mehdi.ticketingSystem.services.DeliveryDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/delivery_details")
public class DeliveryDetailsResource {

    @Autowired
    DeliveryDetailsService deliveryDetailsService;

    @GetMapping("/uncompleted_details_in_priority")
    public ResponseEntity<SuccessResponse<List<DeliveryDetails>>> uncompletedDetailsInPriority() {
        var list = deliveryDetailsService.getAllUncompletedDeliveryDetails();
        return new ResponseEntity<>(
                new SuccessResponse<>("this is list in High to Priority level", list),
                HttpStatus.OK
        );
    }

}
