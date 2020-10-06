package com.denisfeier.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@Data
@ToString
public class Transaction {

    private int personID;
    private int transactedResourceID;
    private float ofPrice;

}
