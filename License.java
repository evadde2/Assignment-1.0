package com.cinco;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/*
 * A concrete Item subclass representing a software or service license.
 */
public class License extends Item {
    private final double annualFee;
    private final double serviceFee;
    private final LocalDate start;
    private final LocalDate end;

    /*
     * Constructor A: Used by DataLoader for Invoice Items (6 parameters)
     */
    public License(String uuid, String name, double serviceFee, double annualFee, LocalDate start, LocalDate end) {
        super(uuid, name, "L");
        this.serviceFee = serviceFee;
        this.annualFee = annualFee;
        this.start = start;
        this.end = end;
    }

    /*
     * Constructor B: Used by ItemReader or Catalog logic (4 parameters)
     */
    public License(String uuid, String name, double serviceFee, double annualFee) {
        this(uuid, name, serviceFee, annualFee, null, null);
    }

    @Override
    public double getCost() {
        if (start == null || end == null) return annualFee;
        long days = ChronoUnit.DAYS.between(start, end) + 1;
        return round(serviceFee + (annualFee * (days / 365.0)));
    }

    @Override
    public double getTax() {
        return 0.0; // Licenses are typically non-taxable in this assignment
    }

    @Override
    public String getDetails() {
        if (start == null) return "";
        long days = ChronoUnit.DAYS.between(start, end) + 1;
        return String.format("%d days (%s -> %s) @ $%.2f /year \n\tService Fee: $%.2f", 
            days, start, end, annualFee, serviceFee);
    }

    @Override
    public double getDisplayTotal() {
        return getCost();
    }

    // Getters for serialization
    public double getAnnualFee() { return annualFee; }
    public double getServiceFee() { return serviceFee; }
    public LocalDate getStart() { return start; }
    public LocalDate getEnd() { return end; }
}
