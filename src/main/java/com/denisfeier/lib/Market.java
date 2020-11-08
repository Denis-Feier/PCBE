package com.denisfeier.lib;

import com.denisfeier.entity.Demand;
import com.denisfeier.entity.Stock;

import java.util.ArrayList;
import java.util.List;

public interface Market {


    Market run() throws Exception;

    Market stop() throws Exception;

    void addSupply(Stock stock);

    void addDemand(Demand demand);

}
