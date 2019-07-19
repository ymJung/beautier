package com.metalbird.beautier.controller.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class BlockSummaryResult {
    
    private long newestBlockNumber;
    private long txBlockCount;
    private double averageGasPrice;
    private double maxGasPrice;
    private double minGasPrice;

    private Map<Double, Integer> orderedPriceTxCntMap;

}
