package com.mehdi.ticketingSystem.comparator;

import com.mehdi.ticketingSystem.comparator.comparatorCalculator.*;
import com.mehdi.ticketingSystem.model.DeliveryDetails;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class DeliveryDetailComparator implements Comparator<DeliveryDetails> {

    private final ComparatorCalculator calculator;

    public DeliveryDetailComparator() {
        calculator = new ComparatorCalculatorBuilder
                (new CustomerTypeComparatorCalculator(10000)).
                addNext(new TimeExceededComparatorCalculator(1000)).
                addNext(new EstimatedTimeComparatorCalculator(100)).
                addNext(new DeliveryIdComparatorCalculator(10)).
                build();
    }

    @Override
    public int compare(DeliveryDetails delivery1, DeliveryDetails delivery2) {
        int value = 0;

        ComparatorCalculator current = calculator;

        while (current != null){
            value += current.calculate(delivery1, delivery2);
            current = current.moveNext();
        }
        return value;
    }

}
