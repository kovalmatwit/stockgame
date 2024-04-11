package com.example.stockgame;

/**
 * Represents a medium-risk stock in the stock market game.
 * Medium-risk stocks have moderate price fluctuations and a slight bias towards positive growth, making them more volatile than low-risk stocks but less so than high-risk stocks.
 */
public class MidRiskStock extends Stock {

    /**
     * Constructs a MidRiskStock instance with specified name, price, and amount owned.
     * The fluctuation range, growth bias, and price floor are predefined for medium-risk stocks.
     *
     * @param name The name (or symbol) of the stock.
     * @param price The initial price of the stock.
     * @param amountOwned The initial amount of this stock owned.
     */
    public MidRiskStock(String name, double price, double amountOwned) {
        // Initializes the medium-risk stock with predefined parameters:
        // Minimum fluctuation: 25%, Maximum fluctuation: 50%, Growth bias: 0.05%, Price floor: 0.1
        super(name, price, amountOwned, 25, 50, 0.05, 0.1);
    }

    /**
     * Returns the risk level of this stock.
     *
     * @return A string indicating this stock's risk level ("Medium").
     */
    @Override
    public String getRiskLevel() {
        return "Medium";
    }
}
