package com.cinco;

import com.google.gson.annotations.SerializedName;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public abstract class Item {
    private final String uuid;

    private final String name;

    private final String type;

    public Item(String uuid, String name, String type) {
        this.uuid = uuid;
        this.name = name;
        this.type = type;
    }

    public abstract double getCost();
    public abstract double getTax();
    public abstract String getDetails();
    public abstract double getDisplayTotal();

    public double getTotal() {
        return round(getCost() + getTax());
    }

    public static double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public String getUuid() { return uuid; }
    public String getName() { return name; }
    public String getType() { return type; }
}

LATEST CODE
    package com.cinco;

import com.google.gson.annotations.SerializedName;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

//Initializes the core identity of any item, including its unique identifier, name, and category type.
public abstract class Item {
    private final String uuid;

    private final String name;

    private final String type;

    public Item(String uuid, String name, String type) {
        this.uuid = uuid;
        this.name = name;
        this.type = type;
    }
    /*
     * Forces subclasses to implement their specific logic for calculating the base 
     * price (before tax).
     */
    public abstract double getCost();
    
    /*
     * Requires subclasses to define how tax is calculated based on their specific item type.
     */
    public abstract double getTax();
    
    /*
     * Return a formatted string containing technical or descriptive metadata unique 
     * to the specific item type.
     */
    public abstract String getDetails();
    
    /*
     * Method for subclasses to provide the final price intended for report formatting.
     */
    public abstract double getDisplayTotal();

    /*
     *Aggregates the results of getCost() and getTax(), returning the rounded final sum.
     */
    public double getTotal() {
        return round(getCost() + getTax());
    }

    /*
     * A utility helper that rounds any double to two decimal places to ensure financial precision and 
     * prevent floating-point errors.
     */
    public static double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    //returns Uuid
    public String getUuid() { return uuid; }
    //returns name
    public String getName() { return name; }
    //returns type
    public String getType() { return type; }
}
