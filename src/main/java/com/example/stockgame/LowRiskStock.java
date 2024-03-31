package com.example.stockgame;

import java.util.Random;

public class LowRiskStock extends Stock {
    public LowRiskStock(String symbol, double price, double amountOwned) {super(symbol, price, amountOwned);}
    @Override
    public String getRiskLevel() {return "Low";}

    @Override
    public void updatePrice() {
        Random random = new Random();
        double percentage = 1 + (99 * random.nextDouble());
        if(random.nextBoolean()){
            setPrice(getPrice() * (1 + (percentage / 100)));
        } else {
            setPrice(getPrice() * (1 - (percentage / 100)));
        }
    }
}

