package com.metalbird.beautier.controller.model;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SummaryResult {
    private String version = "0.1";
    private boolean success = true;
    private String message;

    private GasSummaryResult gasSummaryResult;

    public SummaryResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}