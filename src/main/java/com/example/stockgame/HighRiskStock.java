package com.example.stockgame;

import java.util.Random;

public class HighRiskStock extends Stock {
    public HighRiskStock(String name, double price, double amountOwned) {super(name,price, amountOwned);}
    @Override
    public String getRiskLevel() {return "Risk";}
    @Override
    public void updatePrice() {
        Random random = new Random();
        double percentage = 1 + (299 * random.nextDouble());
        if(random.nextBoolean()){
            setPrice(getPrice() * (1 + (percentage / 300)));
        } else {
            setPrice(getPrice() * (1 - (percentage / 300)));
        }
    }
}
