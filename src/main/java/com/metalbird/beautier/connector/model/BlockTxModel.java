package com.metalbird.beautier.connector.model;

import java.math.BigInteger;
import java.text.DecimalFormat;

import com.metalbird.beautier.controller.model.Unit;

import lombok.Data;

@Data
public class BlockTxModel {
    private String blockHash;
    private String blockNumber;
    private String from;
    private String gas;
    private String gasPrice;
    private String hash;
    private String input;
    private String nonce;
    private String value;

    private String to;
    private String transactionIndex;
    private String r;
    private String s;
    private String v;

    private DecimalFormat format = new DecimalFormat("#.##");

    public double getGasPriceDouble() {
        BigInteger price = new BigInteger(gasPrice);
        price = price.divide(BigInteger.valueOf(Unit.GWEI.getToWei()));
        String priceStr = format.format(price.toString());
        return Double.parseDouble(priceStr);
    }
    /**
 * 이더리움 출력 단위
 */
public enum Unit {
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
}