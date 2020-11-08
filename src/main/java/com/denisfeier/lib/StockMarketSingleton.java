package com.denisfeier.lib;

import com.denisfeier.pool.ThreadPoolImpl;

public class StockMarketSingleton {

    private final static Market ourInstance = getStockMarketInstance();

    public static Market getInstance() {
        return ourInstance;
    }

    private static Market getStockMarketInstance() {
        return new BasicStockMarketBuilder()
                .setEnabledLogger(true)
                .setThreadPool(new ThreadPoolImpl(10))
                .build();
    }
}
