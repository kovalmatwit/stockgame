package com.example.stockgame;

/**
 * Represents a low-risk stock in the stock market game.
 * Low-risk stocks are characterized by smaller price fluctuations and a slight bias towards positive growth.
 */
public class LowRiskStock extends Stock {

    /**
     * Constructs a LowRiskStock instance with specified name, price, and amount owned.
     * The fluctuation range, growth bias, and price floor are predefined for low-risk stocks.
     *
     * @param name The name (or symbol) of the stock.
     * @param price The initial price of the stock.
     * @param amountOwned The initial amount of this stock owned.
     */
    public LowRiskStock(String name, double price, double amountOwned) {
        // Initializes the low-risk stock with predefined parameters:
        // Minimum fluctuation: 1%, Maximum fluctuation: 20%, Growth bias: 1%, Price floor: 0.1
        super(name, price, amountOwned, 1, 20, 1, 10);
    }

    /**
     * Returns the risk level of this stock.
     *
     * @return A string indicating this stock's risk level ("Low").
     */
    @Override
    public String getRiskLevel() {
        return "Low";
    }
}
