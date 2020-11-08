package com.denisfeier.entity;

public class Stock extends StockElement{

    public Stock(double price, int count, Seller owner) {
        super(price, count, owner);
    }

    public void use(int count) {
        this.setCount(getCount() - count);
        this.getOwner().notify(count * this.getPrice());
    }


}
