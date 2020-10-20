package com.denisfeier.entity;

import com.denisfeier.lib.Market;

public class Buyer extends Person {

    public Buyer(Market market) {
        super(market);
    }

    @Override
    public void addToHistory(StockElement stockElement, int amount) {

    }

    public void addDemand(Demand demand) {
        new Thread(()-> {
            synchronized (this.market) {
                this.market.addDemand(demand);
            }
            Thread.currentThread().interrupt();
        }).start();
    }

}
