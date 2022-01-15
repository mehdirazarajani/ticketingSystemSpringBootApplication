package com.mehdi.ticketingSystem.domain;

public enum DeliveryStatus {
    ORDER_RECEIVED,
    ORDER_PREPARING,
    ORDER_PICKED,
    ORDER_DELIVERED;

    boolean isGreaterThan(DeliveryStatus status){
        return this.ordinal() > status.ordinal();
    }

    boolean isLesserThan(DeliveryStatus status){
        return this.ordinal() < status.ordinal();
    }

    public boolean isOutForDelivery(){
        return this.isGreaterThan(ORDER_PREPARING);
    }

    public static DeliveryStatus getByName(String string){
        try {
            return DeliveryStatus.valueOf(string.toUpperCase());
        } catch (Exception e){
            return DeliveryStatus.ORDER_RECEIVED;
        }
    }
}
