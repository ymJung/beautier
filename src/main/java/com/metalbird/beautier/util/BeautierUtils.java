package com.metalbird.beautier.util;


import java.math.BigInteger;

public class BeautierUtils {

    public double getHexToDouble(String hexNumStr) {
        String priceStr = getFormattedStrNumber(hexNumStr);
        return Double.parseDouble(priceStr);
    }
    public long getHexToLong(String hexNumStr) {
        String priceStr = getFormattedStrNumber(hexNumStr);
        return Long.parseLong(priceStr);
    }

    private String getFormattedStrNumber(String hexNumStr) {
        BigInteger price = getDecimalFromHex(hexNumStr);
        price = price.divide(BigInteger.valueOf(Unit.GWEI.getToWei()));
        return StaticValues.DECIMAL_FORMAT.format(price);
    }

    private BigInteger getDecimalFromHex(String hexNumStr) {
        if (hexNumStr.startsWith(StaticValues.START_HEX)) {
            hexNumStr = hexNumStr.substring(StaticValues.START_HEX.length());
        }
        return new BigInteger(hexNumStr, StaticValues.HEX);
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

