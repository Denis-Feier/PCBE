package com.denisfeier.lib;

import com.denisfeier.entity.Demand;
import com.denisfeier.entity.Stock;
import com.denisfeier.pool.ThreadPool;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MarketImpl implements Market {
    private final Object SUPPLY_LOCK = new Object();
    private final Object HISTORY_LOCK = new Object();
    private final Object DEMAND_LOCK = new Object();

    private List<String> history = new ArrayList<String>();
    private List<Stock> supplies = new ArrayList<Stock>();
    private List<Demand> demands = new ArrayList<Demand>();
    private boolean isRunning = false;
    private boolean enabledLogger = false;

    private ThreadPool threadPool;

    public void setEnabledLogger(boolean enabledLogger) {
        this.enabledLogger = enabledLogger;
    }

    public void setThreadPool(ThreadPool threadPool) {
        this.threadPool = threadPool;
    }

    public void addSupply(Stock stock) {
        synchronized (SUPPLY_LOCK) {
            supplies.add(stock);
        }
        this.updateHistory(stock.getOwner().getName() + " with id " + stock.getOwner().getIdentifier() + " added a supply :" + stock.toString());

    }

    public void addDemand(final Demand demand) {
        synchronized (DEMAND_LOCK) {
            this.demands.add(demand);
        }
        this.updateHistory(demand.getOwner().getName() + " with id " + demand.getOwner().getIdentifier() + " added a demand :" + demand.toString());

        this.threadPool.execute(new Runnable() {
            public void run() {
                matchDemandWithSupply(demand);
            }
        });
    }

    public Market run() throws RuntimeException {
        if (this.isRunning) {
            throw new RuntimeException("The StockMarket is already running");
        }
        this.isRunning = true;
        this.threadPool.restart();
        return this;
    }

    public Market stop() throws RuntimeException {
        if (!this.isRunning) {
            throw new RuntimeException("The StockMarket is already stopped");
        }
        this.isRunning = false;
        this.threadPool.shutdown();
        return this;
    }

    /**
     * Method to print every action that took place in the market
     *
     * @return The whole history as a String
     */
    public String printHistory() {
        StringBuilder fullHistory = new StringBuilder();

        for (int i = 0; i < history.size(); i++) {
            fullHistory.append(history.get(i) + "\n");
        }

        return fullHistory.toString();
    }

    private void tryToBuy(Demand demand, Stock stock) {
        if (demand.getPrice() != stock.getPrice() || stock.getCount() == 0 || demand.getCount() == 0) {
            return;
        }

        int min = Math.min(stock.getCount(), demand.getCount());

        synchronized (SUPPLY_LOCK) {

            stock.use(min);

        }
        synchronized (DEMAND_LOCK) {

            demand.use(min);

        }

        this.updateHistory("[" + Thread.currentThread() + "]:" + demand.getOwner().getName() + " with the demand " + demand.toString() + " matched " + stock.toString());


    }
    private void removeDemand(Demand demand) {
        synchronized (DEMAND_LOCK) {
            this.demands.remove(demand);

        }
        this.updateHistory("[" + Thread.currentThread() + "]:" + demand.toString() + " demand has ben consumed");

    }

    private void updateHistory(String message) {
        synchronized (HISTORY_LOCK) {
            history.add(new Timestamp(System.currentTimeMillis()).getTime() + " : " + message);
            if (enabledLogger)
                System.out.println(new Timestamp(System.currentTimeMillis()).getTime() + " : " + message);
        }
    }

    private void removeSupply(Stock stock) {
        synchronized (SUPPLY_LOCK) {
            supplies.remove(stock);
        }
        this.updateHistory("[" + Thread.currentThread() + "]:" + stock.toString() + " supply has ben removed");

    }

    private void matchDemandWithSupply(Demand demand) {
        int timesTriedToBuy = 0;

        for (;;) {
//            synchronized (SUPPLY_LOCK) {
            for (int i = 0; i < this.supplies.size() && this.supplies.get(i) != null; i++) {
                Stock stock = this.supplies.get(i);
                tryToBuy(demand, stock);

                if (stock.getCount() == 0) {
                    removeSupply(stock);
                }

                if (demand.getCount() == 0) {
                    removeDemand(demand);
                    return;
                }
            }
            if (timesTriedToBuy++ == 2000) {
                return;
            }
//            }
        }
    }
}
