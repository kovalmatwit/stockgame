package com.example.stockgame;

import java.util.Random;

public class MidRiskStock extends Stock {

    public MidRiskStock(String name, double price, double amountOwned) {super(name, price, amountOwned);}
    @Override
    public String getRiskLevel() {return "Medium";}
    @Override
    public void updatePrice() {
        Random random = new Random();
        double percentage = 1 + (199 * random.nextDouble());
        if(random.nextBoolean()){
            setPrice(getPrice() * (1 + (percentage / 200)));
        } else {
            setPrice(getPrice() * (1 - (percentage / 200)));
        }
    }
}
