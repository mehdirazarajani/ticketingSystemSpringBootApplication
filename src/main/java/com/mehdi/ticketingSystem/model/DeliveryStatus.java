package com.mehdi.ticketingSystem.model;

public enum DeliveryStatus {
    ORDER_RECEIVED,
    ORDER_PREPARING,
    ORDER_PICKED,
    ORDER_DELIVERED;

    public boolean isGreaterThan(DeliveryStatus status){
        return this.ordinal() > status.ordinal();
    }

    public boolean isLesserThan(DeliveryStatus status){
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

    public static boolean isValidName(String name){
        try {
            DeliveryStatus.valueOf(name.toUpperCase());
            return true;
        } catch (Exception e){
            return false;
        }
    }

}
