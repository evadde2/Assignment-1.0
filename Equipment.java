package com.cinco;

/*
 * A concrete Item subclass representing equipment.
 * Supports both Catalog definitions and Transactional invoice items.
 */
public class Equipment extends Item {
    private final double costPerUnit;
    private final String status;
    private final int quantity;
    private final double PURCHASE__TAX__RATE = 0.0525;

    /*
     * Constructor A: Used by DataLoader for Invoice Items (5 parameters)
     */
    public Equipment(String uuid, String name, double costPerUnit, String status, int quantity) {
        super(uuid, name, "E");
        this.costPerUnit = costPerUnit;
        this.status = status;
        this.quantity = quantity;
    }

    /*
     * Constructor B: Used by ItemReader or Catalog logic (3 parameters)
     * This handles the "The constructor Equipment(String, String, double) is undefined" error.
     */
    public Equipment(String uuid, String name, double costPerUnit) {
        this(uuid, name, costPerUnit, "NONE", 1);
    }

    @Override 
    public double getCost() { 
        // Rounds the total cost: price * quantity
        return round(costPerUnit * quantity); 
    }

    @Override 
    public double getTax() { 
        // Rounds the tax based on the total cost
        return round(getCost() * PURCHASE__TAX__RATE); 
    }

    @Override 
    public String getDetails() { 
        // Returns an empty string for catalog items, or formatted details for invoice items
        if (status.equals("NONE")) return "";
        return String.format("%d units at $%.2f/unit (%s)", quantity, costPerUnit, status); 
    }

    @Override 
    public double getDisplayTotal() { 
        return getCost(); 
    }

    // Getters for serialization and logic
    public double getCostPerUnit() { return costPerUnit; }
    public String getStatus() { return status; }
    public int getQuantity() { return quantity; }
    public double getPurchaseTaxRate() { return PURCHASE__TAX__RATE; }
}
