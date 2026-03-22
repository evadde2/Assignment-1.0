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

Latest code
    package com.cinco;

import com.google.gson.annotations.SerializedName;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/*The Equipment class models tangible assets by calculating unit-based costs and a 5.25% tax while using annotations 
 * to control data visibility in exports.
 */

@XStreamAlias("linkedhashmap")
public class Equipment extends Item {
    
    @SerializedName("CostPerUnit")
    private final String costPerUnit; 

    // These fields are needed for InvoiceReports but must be hidden from JSON/XML
    @XStreamOmitField
    private transient final String status;
    
    @XStreamOmitField
    private transient final int quantity;

    @XStreamOmitField
    private final double PURCHASE__TAX__RATE = 0.0525;

    //The primary constructor used to initialize a full equipment record.
    public Equipment(String uuid, String name, double costPerUnit, String status, int quantity) {
        super(uuid, name, "E");
        this.costPerUnit = String.format("%.1f", costPerUnit);
        this.status = status;
        this.quantity = quantity;
    }

    //A simplified "overloaded" constructor typically used during initial data loading.
    public Equipment(String uuid, String name, double costPerUnit) {
        this(uuid, name, costPerUnit, "NONE", 1);
    }
//returns status value
    public String getStatus() {
        return status;
    }
//returns quantity value
    public int getQuantity() {
        return quantity;
    }

    //returns the cost 
    @Override
    public double getCost() {
        return Double.parseDouble(costPerUnit);
    }
    //returns the tax
    @Override
    public double getTax() {
        return round(getCost() * PURCHASE__TAX__RATE);
    }

    //returns the details
    @Override
    public String getDetails() {
        return String.format("%s (%d units at $%s/unit)", status, quantity, costPerUnit);
    }

    //returns the overall total
    @Override
    public double getDisplayTotal() {
        return getCost() * quantity;
    }
}
