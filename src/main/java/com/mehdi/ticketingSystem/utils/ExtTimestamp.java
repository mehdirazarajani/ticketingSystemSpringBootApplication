package com.mehdi.ticketingSystem.utils;

import java.sql.Timestamp;

public class ExtTimestamp extends Timestamp {

    public ExtTimestamp(Timestamp timestamp){
        super(timestamp.getTime());
    }

    public ExtTimestamp(long time) {
        super(time);
    }

    public long timeExceededWithBaseTime(Timestamp baseTime) {
        if (baseTime.before(this)) {
            return this.getTime() - baseTime.getTime();
        }
        return 0;
    }

    public long timeExceeded(){
        Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
        return this.timeExceededWithBaseTime(currentTimeStamp);
    }

    long difference(Timestamp timestamp){
        return this.getTime() - timestamp.getTime();
    }

    public int absoluteDifferenceInMinutes(Timestamp timestamp){
        return (int) difference(timestamp) / (1000 * 60);
    }

    public long timeInMinutes(){
        return this.getTime() / (1000 * 60);
    }

}
