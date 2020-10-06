package com.denisfeier.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Stock {
    private int id;
    private float price;
    private int owner;
}
