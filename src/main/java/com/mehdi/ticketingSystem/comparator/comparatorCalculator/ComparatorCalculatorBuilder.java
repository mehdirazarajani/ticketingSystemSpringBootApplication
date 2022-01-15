package com.mehdi.ticketingSystem.comparator.comparatorCalculator;

public class ComparatorCalculatorBuilder {

    ComparatorCalculator calculator;

    public ComparatorCalculatorBuilder(ComparatorCalculator calculator){
        this.calculator = calculator;
    }

    public ComparatorCalculatorBuilder addNext(ComparatorCalculator nextCalculator){
        ComparatorCalculator current = calculator;
        while (current.next != null){
            current = current.next;
        }
        current.next = nextCalculator;
        return this;
    }

    public ComparatorCalculator build(){
        return calculator;
    }
}
