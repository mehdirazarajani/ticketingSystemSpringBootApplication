package com.mehdi.ticketingSystem.model.dtos;

import com.mehdi.ticketingSystem.model.CustomerType;
import com.mehdi.ticketingSystem.model.DeliveryStatus;
import com.mehdi.ticketingSystem.utils.EnumValue;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class InsertDeliveryDetailsDTO {

    @NotNull(message = "The deliveryId is required.")
    int deliveryId;

    @NotNull(message = "The customerType is required.")
//    @EnumValue(enumClass = CustomerType.class, enumMethod = "isValidName")
    CustomerType customerType;

    @NotNull(message = "The deliveryStatus is required.")
//    @EnumValue(enumClass = DeliveryStatus.class, enumMethod = "isValidName")
    DeliveryStatus deliveryStatus;

    @NotNull(message = "The expectedDeliveryTime is required.")
    long expectedDeliveryTime;

    @NotNull(message = "The currentDistanceFromDestinationInMeters is required.")
    int currentDistanceFromDestinationInMeters;

    @NotNull(message = "The timeToReachDestination is required.")
    long timeToReachDestination;

}
