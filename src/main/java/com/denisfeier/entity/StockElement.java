package com.denisfeier.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public abstract class StockElement {
    private double price;
    private int count;
    private Person owner;

    public abstract void use(int count);
}
