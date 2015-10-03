package com.amdudda;

/**
 * Created by amdudda on 10/3/15.
 */
public class Beverage {
    // Beverage object to store data about beverages

    // attributes of class beverage
    // using primitives because there's no real reason as of this writing to wrap them
    private String name;
    private double cost;
    private double price;
    private int qty_sold;

    // Constructor
    public Beverage (String bevName, double bevCost, double bevPrice) {
        // initializing Beverage object
        this.name = bevName;
        this.cost = bevCost;
        this.price = bevPrice;
        this.qty_sold = 0; // we won't know how many have been sold until user enters info.
    }  // end constructor

    // a whole bunch of getter and setter methods.
    // pro forma, because I think this assignment will only use set & get qty_sold.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty_sold() {
        return qty_sold;
    }

    public void setQty_sold(int qty_sold) {
        this.qty_sold = qty_sold;
    }

}
