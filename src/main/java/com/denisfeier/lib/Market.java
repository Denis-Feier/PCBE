package com.denisfeier.lib;

import com.denisfeier.entity.Stock;

import java.util.ArrayList;
import java.util.List;

public class Market {

    private static final Market market = new Market();

    public List<Stock> stocks = new ArrayList<>();

    private Market() {}

    public static Market shared() {
        return market;
    }

    public static synchronized void addStock(Stock stock) {
        Market.shared().stocks.add(stock);
    }

    public static synchronized List<Stock> getStocks() {
        return Market.shared().stocks;
    }

}
