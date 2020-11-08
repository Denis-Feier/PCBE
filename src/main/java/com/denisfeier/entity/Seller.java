package com.denisfeier.entity;

import com.denisfeier.lib.Market;

public class Seller extends Person {

    public Seller(String instanceIdentifier, Market stockMarket) {
        super(instanceIdentifier, stockMarket);
    }

    public void createSupply(double price, int count) {
        final Seller self = this;
        final Stock stock = new Stock(price, count, this);
        new Thread(new Runnable() {
            public void run() {
                synchronized (self.stockMarket) {
                    self.stockMarket.addSupply(stock);
                }
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    @Override
    public void notify(double cost) {
         System.out.println("Seller: " + super.getIdentifier() + " sold an action with " + cost);
    }


}
