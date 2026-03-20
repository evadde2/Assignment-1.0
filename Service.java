package com.cinco;

/*
 * A concrete Item subclass representing billable labor.
 */
public class Service extends Item {
    private final double costPerHour;
    private final Person servicePerson;
    private final double hours;
    private final double SERVICE__FLAT__FEE = 125.0;
    private final double SERVICE__TAX__RATE = 0.0315;

    /*
     * Constructor A: Used by DataLoader for Invoice Items (5 parameters)
     */
    public Service(String uuid, String name, double costPerHour, Person servicePerson, double hours) {
        super(uuid, name, "S");
        this.costPerHour = costPerHour;
        this.servicePerson = servicePerson;
        this.hours = hours;
    }

    /*
     * Constructor B: Used by ItemReader or Catalog logic (3 parameters)
     */
    public Service(String uuid, String name, double costPerHour) {
        this(uuid, name, costPerHour, null, 0.0);
    }

    @Override
    public double getCost() {
        return round((hours * costPerHour) + SERVICE__FLAT__FEE);
    }

    @Override
    public double getTax() {
        return round(getCost() * SERVICE__TAX__RATE);
    }

    @Override
    public String getDetails() {
        if (servicePerson == null) return "";
        return String.format("%.2f hours @ $%.2f/unit \n\tServiced by %s, %s", 
            hours, costPerHour, 
            servicePerson.getLastName(), servicePerson.getFirstName());
    }

    @Override
    public double getDisplayTotal() {
        return getCost();
    }

    // Getters for serialization
    public double getCostPerHour() { return costPerHour; }
    public Person getServicePerson() { return servicePerson; }
    public double getHours() { return hours; }
}
