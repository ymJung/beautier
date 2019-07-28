package com.metalbird.beautier.controller.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class BlockSummaryResult {
    
    private long newestBlockNumber;
    private long txBlockCount;
    private Map<String, Integer> orderedPriceTxCntMap;
    private String averageGasPrice;
    private String maxGasPrice;
    private String minGasPrice;



}
