package com.example.stockgame;

/**
 * Represents a high-risk stock in the stock market game.
 * High-risk stocks have large price fluctuations and no growth bias, presenting the highest volatility among the stock types. These stocks can offer high rewards but also come with high risks.
 */
public class HighRiskStock extends Stock {

    /**
     * Constructs a HighRiskStock instance with specified name, price, and amount owned.
     * The fluctuation range and growth bias are predefined for high-risk stocks to reflect their volatile nature.
     *
     * @param name The name (or symbol) of the stock.
     * @param price The initial price of the stock.
     * @param amountOwned The initial amount of this stock owned.
     */
    public HighRiskStock(String name, double price, double amountOwned) {
        // Initializes the high-risk stock with predefined parameters:
        // Minimum fluctuation: 75%, Maximum fluctuation: 150%, Growth bias: 0%, Price floor: 0
        super(name, price, amountOwned, 75, 150, 0, 0);
    }

    /**
     * Returns the risk level of this stock.
     *
     * @return A string indicating this stock's risk level ("High").
     */
    @Override
    public String getRiskLevel() {
        return "High";
    }
}
