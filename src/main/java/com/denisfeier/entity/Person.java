package com.denisfeier.entity;

import com.denisfeier.lib.Market;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Random;
import java.util.UUID;

@Data
@AllArgsConstructor
@ToString
public abstract class Person {
    Market stockMarket;
    private String identifier;
    private String name;

    Person(String instanceIdentifier, Market stockMarket) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();


        this.identifier = generatedString;
        this.name = instanceIdentifier;
        this.stockMarket = stockMarket;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public String getName() {
        return this.name;
    }

    abstract void notify(double cost);



}
