package com.denisfeier.entity;

import com.denisfeier.lib.Market;

public class Buyer extends Person {

    public Buyer(String instanceIdentifier, Market stockMarket) {
        super(instanceIdentifier, stockMarket);
    }

    @Override
    void notify(double cost) {
         System.out.println("Buyer: " + super.getIdentifier() + " bought an action with " + cost);
    }

    public void addDemand(final Demand demand) {
        final Buyer self = this;
        new Thread(new Runnable() {
            public void run() {
                synchronized (self.stockMarket) {
                    self.stockMarket.addDemand(demand);
                }
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
