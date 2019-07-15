package com.metalbird.beautier.controller.model;

import lombok.Data;
import lombok.Getter;

import java.math.BigInteger;
import java.text.DecimalFormat;

@Data
public class GasSummaryResult {
    @Getter
    private Unit unit;
    @Getter
    private DecimalFormat format;

    private BigInteger avgPrice;
    private BigInteger maxPrice;
    private BigInteger minPrice;

    public GasSummaryResult(BigInteger weiAvgPrice, BigInteger weiMaxPrice, BigInteger weiMinPrice) {
        unit = Unit.GWEI;
        format = new DecimalFormat("#.##"); // 기본값 = 반올림
        avgPrice = weiAvgPrice.divide(BigInteger.valueOf(unit.getToWei()));
        maxPrice = weiMaxPrice.divide(BigInteger.valueOf(unit.getToWei()));
        minPrice = weiMinPrice.divide(BigInteger.valueOf(unit.getToWei()));
    }

    public String getAvgPrice() {
        return format.format(avgPrice.toString());
    }

    public String getMaxPrice() {
        return format.format(maxPrice.toString());
    }

    public String getMinPrice() {
        return format.format(minPrice.toString());
    }

}

/**
 * 이더리움 출력 단위
 */
enum Unit {
    WEI("WEI", 1), GWEI("Gwei", 1_000_000_000);
    private String name;
    private long toWei; // 만약 2147483647 값이 넘는게 오면 개선이 필요함.

    Unit(String name, int toWei) {
        this.name = name;
        this.toWei = toWei;
    }

    public long getToWei() {
        return toWei;
    }


    // WEI	1	0.000000000000000001
    // Ada	1000	0.000000000000001
    // Fentoether	1000	0.000000000000001
    // Kwei	1000	0.000000000000001
    // Mwei	1000000	0.000000000001
    // Babbage	1000000	0.000000000001
    // Pictoether	1000000	0.000000000001
    // Shannon	1000000000	0.000000001
    // Gwei	1000000000	0.000000001
    // Nano	1000000000	0.000000001
    // Szabo	1000000000000	0.000001
    // Micro	1000000000000	0.000001
    // Microether	1000000000000	0.000001
    // Finney	1000000000000000	0.001
    // Milli	1000000000000000	0.001
    // Milliether	1000000000000000	0.001
    // Ether	1000000000000000000	1
    // Einstein	1000000000000000000000	1000
    // Kether	1000000000000000000000	1000
    // Grand	1000000000000000000000	1000
    // Mether	1000000000000000000000000	1000000
    // Gether	1000000000000000000000000000	1000000000
    // Tether	1000000000000000000000000000000	1000000000000
}