package com.denisfeier;

import com.denisfeier.entity.Buyer;
import com.denisfeier.entity.Demand;
import com.denisfeier.entity.Seller;
import com.denisfeier.lib.Market;
import com.denisfeier.lib.StockMarketSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PCBE {
    public static void main(String[] args) {
        Market stockMarket = StockMarketSingleton.getInstance();
        try {
            stockMarket.run();
            initApp(2000, 2000, stockMarket);

            Thread.sleep(1000);
            stockMarket.stop();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void initApp(int numberOfSellers, int numberOfBuyers, Market market) {
        final Random rnd = new Random();

        List<Seller> sellers = new ArrayList<Seller>();
        List<Buyer> buyers = new ArrayList<Buyer>();

        for (int i = 0; i < numberOfSellers; i++) {
            sellers.add(new Seller("Seller" + i, market));
        }

        for (int i = 0; i < numberOfBuyers; i++) {
            buyers.add(new Buyer("Buyer" + i, market));
        }

        for (final Seller s : sellers) {
            s.createSupply(rnd.nextInt(2) + 1, rnd.nextInt(4) + 1);
        }

        for (final Buyer b : buyers) {
            b.addDemand(new Demand(rnd.nextInt(2) + 1, rnd.nextInt(4) + 1, b));
        }

    }
}
