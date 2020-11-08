package com.denisfeier.entity;

public class Demand extends StockElement {

    public Demand(double price, int count, Buyer owner) {
        super(price, count, owner);
    }

    public void use(int count) {
        this.setCount(this.getCount() - count);
        this.getOwner().notify(count);
    }
}
