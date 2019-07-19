package com.metalbird.beautier.controller.model;


import com.metalbird.beautier.util.BeautierUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SummaryResult {
    private String version = "0.1";
    private BeautierUtils.Unit unit = BeautierUtils.Unit.GWEI;
    private boolean success = true;
    private String message = "";
    

    private BlockSummaryResult blockSummaryResult;

    public SummaryResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public SummaryResult(BlockSummaryResult blockSummaryResult) {
        this.blockSummaryResult = blockSummaryResult;
    }
}