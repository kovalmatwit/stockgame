package com.example.stockgame;

import java.util.Random;

/**
 * Abstract class representing a stock in the stock market game.
 * It defines the basic properties and behavior that all types of stocks share.
 */
public abstract class Stock {
    // Name of the stock (also can be thought of as the symbol).
    private final String name;
    // Current price of the stock.
    private double price;
    // Amount of this stock owned.
    private double amountOwned;
    // Minimum percentage fluctuation in price.
    private final double MIN_FLUCTUATION;
    // Maximum percentage fluctuation in price.
    private final double MAX_FLUCTUATION;
    // A bias added to the fluctuation to simulate growth or decline trends.
    private final double GROWTH_BIAS;
    // The lowest possible price of the stock to prevent negative values.
    private final double PRICE_FLOOR;

    /**
     * Constructor to initialize a stock with its properties.
     *
     * @param symbol Symbol of the stock.
     * @param price Initial price of the stock.
     * @param amountOwned Initial amount of this stock owned.
     * @param MIN_FLUCTUATION Minimum fluctuation percentage.
     * @param MAX_FLUCTUATION Maximum fluctuation percentage.
     * @param GROWTH_BIAS Growth bias to influence the stock's trend.
     * @param PRICE_FLOOR The lowest price the stock can fall to.
     */
    public Stock(String symbol, double price, double amountOwned, double MIN_FLUCTUATION, double MAX_FLUCTUATION, double GROWTH_BIAS, double PRICE_FLOOR) {
        this.name = symbol;
        this.price = price;
        this.amountOwned = amountOwned;
        this.MIN_FLUCTUATION = MIN_FLUCTUATION;
        this.MAX_FLUCTUATION = MAX_FLUCTUATION;
        this.GROWTH_BIAS = GROWTH_BIAS;
        this.PRICE_FLOOR = PRICE_FLOOR;
    }

    // Getters and setters for stock properties.
    public String getName() { return name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public double getAmountOwned() { return amountOwned; }
    public void setAmountOwned(double amountOwned) { this.amountOwned = amountOwned; }

    /**
     * Abstract method to get the risk level of the stock.
     * To be implemented by subclasses.
     *
     * @return String representing the risk level of the stock.
     */
    public abstract String getRiskLevel();

    /**
     * Updates the price of the stock based on its fluctuation range,
     * growth bias, and ensures it doesn't fall below the price floor.
     */
    public void updatePrice() {
        Random random = new Random();
        // Calculate the range of fluctuation.
        double fluctuationRange = MAX_FLUCTUATION - MIN_FLUCTUATION;
        // Randomly determine the fluctuation within the range.
        double fluctuation = MIN_FLUCTUATION + (fluctuationRange * random.nextDouble());
        // Randomly decide if the price goes up or down.
        boolean goesUp = random.nextBoolean();

        // Apply the growth bias to the fluctuation.
        double adjustedFluctuation = (goesUp ? 1 : -1) * fluctuation + GROWTH_BIAS;

        // Calculate the new price with the adjusted fluctuation.
        double newPrice = getPrice() * (1 + adjustedFluctuation / 100);

        // Ensure the new price is not below the floor.
        newPrice = Math.max(newPrice, PRICE_FLOOR);

        // Set the new price.
        setPrice(newPrice);
    }

}
