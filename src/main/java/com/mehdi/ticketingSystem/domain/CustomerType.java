package com.mehdi.ticketingSystem.domain;

public enum CustomerType {
    NEW(1),
    LOYAL(1),
    VIP(2);

    private final int level;

    CustomerType(int level) {
        this.level = level;
    }

    public int level(){
        return level;
    }

    boolean isGreaterThan(CustomerType type){
        return this.ordinal() > type.ordinal();
    }

    boolean isLesserThan(CustomerType type){
        return this.ordinal() < type.ordinal();
    }

    public boolean isUnderVIPCategory(){
        return this.isLesserThan(VIP);
    }

    public static CustomerType getByName(String string){
        try {
            return CustomerType.valueOf(string.toUpperCase());
        } catch (Exception e){
            return CustomerType.NEW;
        }
    }
}
