package com.metalbird.beautier.controller.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class BlockSummaryResult {
    
    private long newestBlockNumber;
    private long txBlockCount;
    private Map<Double, Integer> orderedPriceTxCntMap;
    private double avgPrice;
    private double maxPrice;
    private double minPrice;


   
}
