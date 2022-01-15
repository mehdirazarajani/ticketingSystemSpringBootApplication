package com.mehdi.ticketingSystem.domain;

import com.mehdi.ticketingSystem.utils.ExtTimestamp;

import javax.swing.text.html.Option;
import java.sql.Timestamp;
import java.util.Optional;

public class DeliveryDetails {

    public final static long MEAN_TIME_TO_PREPARE_FOOD = 30 * 60 * 1000; // 30 minutes
    public static String TABLE_NAME = "DELIVERY_DETAILS";
    public static String DELIVERY_ID = "delivery_id";
    public static String CUSTOMER_TYPE = "customer_type";
    public static String DELIVERY_STATUS = "delivery_status";
    public static String EXPECTED_DELIVERY_TIME = "expected_delivery_time";
    public static String CURRENT_DISTANCE_FROM_DESTINATION_IN_METERS = "current_distance_from_destination_in_meters";
    public static String TIME_TO_REACH_DESTINATION = "time_to_reach_destination";
    public static String ESTIMATED_TIME = "estimated_time";

    int deliveryId;
    CustomerType customerType;
    DeliveryStatus deliveryStatus;
    ExtTimestamp expectedDeliveryTime;
    int currentDistanceFromDestinationInMeters;
    ExtTimestamp timeToReachDestination;

    public DeliveryDetails(int deliveryId, CustomerType customerType, DeliveryStatus deliveryStatus, Timestamp expectedDeliveryTime, int currentDistanceFromDestinationInMeters, Timestamp timeToReachDestination) {
        this.deliveryId = deliveryId;
        this.customerType = customerType;
        this.deliveryStatus = deliveryStatus;
        this.expectedDeliveryTime = new ExtTimestamp(expectedDeliveryTime);
        this.currentDistanceFromDestinationInMeters = currentDistanceFromDestinationInMeters;
        this.timeToReachDestination = new ExtTimestamp(timeToReachDestination);
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Timestamp getExpectedDeliveryTime() {
        return expectedDeliveryTime;
    }

    public void setExpectedDeliveryTime(Timestamp expectedDeliveryTime) {
        this.expectedDeliveryTime = new ExtTimestamp(expectedDeliveryTime);
    }

    public int getCurrentDistanceFromDestinationInMeters() {
        return currentDistanceFromDestinationInMeters;
    }

    public void setCurrentDistanceFromDestinationInMeters(int currentDistanceFromDestinationInMeters) {
        this.currentDistanceFromDestinationInMeters = currentDistanceFromDestinationInMeters;
    }

    public ExtTimestamp getTimeToReachDestination() {
        return timeToReachDestination;
    }

    public void setTimeToReachDestination(Timestamp timeToReachDestination) {
        this.timeToReachDestination = new ExtTimestamp(timeToReachDestination);
    }

    public Optional<Integer> timeExceeded() {
        return exceedTimeInMinutes(expectedDeliveryTime.timeExceeded());
    }

    public ExtTimestamp estimatedTimeOfDelivery(){
        return new ExtTimestamp(timeToReachDestination.getTime() + MEAN_TIME_TO_PREPARE_FOOD);
    }

    public Optional<Integer> estimatedTimeCanExceed(){
        return exceedTimeInMinutes(estimatedTimeOfDelivery().timeExceededWithBaseTime(expectedDeliveryTime));
    }

    private Optional<Integer> exceedTimeInMinutes(long exceededTime){
        int value = (int) exceededTime / (1000 * 60); // penalize on minutes
        return value > 0 ? Optional.of(value) : Optional.empty();
    }
}
