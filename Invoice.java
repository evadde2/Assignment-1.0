package com.cinco;
/*
 * This core entity class aggregates customer data, sales personnel, and a collection of billed items to 
 * calculate comprehensive tax and cost totals for a specific transaction date.
 */

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Invoice {

    private final String uuid;
    private final Company customer;
    private final Person salesperson;
    private final LocalDate date;
    private final List<Item> items = new ArrayList<>();

    /*
     * Initializes the invoice with a unique identifier, associated customer company, salesperson, 
     * and the transaction date.
     */
    public Invoice(String uuid, Company customer, Person salesperson, LocalDate date) {
        this.uuid = uuid;
        this.customer = customer;
        this.salesperson = salesperson;
        this.date = date;
    }

    /*
     * Appends a specific Item subclass instance (Equipment, Service, or License) to the invoice's 
     * internal collection.
     */
    public void addItem(Item item) {
        items.add(item);
    }


     // Returns the unique string identifier for the invoice.
    public String getUuid() { return uuid; }
    
    //Returns the Company object representing the client billed for the transaction.
    public Company getCustomer() { return customer; }
    
    //Returns the Person object representing the employee who facilitated the sale.
    public Person getSalesperson() { return salesperson; }
    
    //Returns the LocalDate indicating when the invoice was issued.
    public LocalDate getDate() { return date; }
    
    //Returns the list of all individual line items associated with the invoice.
    public List<Item> getItems() { return items; }
    
    //A helper method that retrieves the rounded total tax for all items.
 double getTax() {
        return getTotalTax();
    }

//A helper method that retrieves the final rounded grand total (subtotal plus tax).
    public double getTotal() {
        return getGrandTotal();
    }

    /*
     * Calculates the sum of all taxes for all items in the invoice
     */
    public double getTotalTax() {
        double totalTax = 0.0;
        for (Item item : this.items) {
            totalTax += item.getTax();
        }
        return Math.round(totalTax * 100.0) / 100.0;
    }

    /*
     * Calculates the sum of all item costs (pre-tax).
     */
    public double getSubTotal() {
        double subTotal = 0.0;
        for (Item item : this.items) {
            subTotal += item.getCost();
        }
        return Math.round(subTotal * 100.0) / 100.0;
    }
/*
 * The final amount: Subtotal + Total Tax.
 */
    public double getGrandTotal() {
        return Math.round((getSubTotal() + getTotalTax()) * 100.0) / 100.0;
    }
}
