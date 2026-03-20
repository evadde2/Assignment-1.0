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
