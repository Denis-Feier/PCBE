package com.denisfeier.lib;

import com.denisfeier.pool.ThreadPool;
import com.denisfeier.pool.ThreadPoolImpl;

public class BasicStockMarketBuilder {
    private MarketImpl stockMarket;

    private ThreadPool defaultThreadPool = new ThreadPoolImpl(5);

    BasicStockMarketBuilder() {
        this.stockMarket = new MarketImpl();
        this.stockMarket.setEnabledLogger(true);
        this.stockMarket.setThreadPool(defaultThreadPool);
    }

    BasicStockMarketBuilder setEnabledLogger(boolean enabledLogger) {
        this.stockMarket.setEnabledLogger(enabledLogger);
        return this;
    }

    BasicStockMarketBuilder setThreadPool(ThreadPool threadPool) {
        this.defaultThreadPool.shutdown();
        this.stockMarket.setThreadPool(threadPool);
        return this;
    }

    Market build() {
        return this.stockMarket;
    }
}
