package com.example.stockgame;

public abstract class Stock {
    private final String name;
    private double price;
    private double amountOwned;

    public Stock(String symbol, double price, double amountOwned) {
        this.name = symbol;
        this.price = price;
        this.amountOwned = amountOwned;
    }

    public String getName() {return name;}
    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}
    public double getAmountOwned() {return amountOwned;}
    public void setAmountOwned(double amountOwned) {this.amountOwned = amountOwned;}
    public abstract String getRiskLevel();
    public abstract void updatePrice();

}
