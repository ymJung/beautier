package com.metalbird.beautier.util;


import java.math.BigDecimal;
import java.math.BigInteger;

public class BeautierUtils {

    public double getHexToDouble(String hexNumStr) {
        BigDecimal number = getGweiNumberFromHexStr(hexNumStr);
        return number.doubleValue();
    }
    public long getHexToLong(String hexNumStr) {
        BigDecimal hexNum = getDecimalFromHex(hexNumStr);
        return hexNum.longValue();
    }

    private BigDecimal getGweiNumberFromHexStr(String hexNumStr) {
        BigDecimal price = getDecimalFromHex(hexNumStr);
        return price.divide(BigDecimal.valueOf(StaticValues.UNIT.getToWei()));
    }

    public BigDecimal getDecimalFromHex(String hexNumStr) {
        if (hexNumStr.startsWith(StaticValues.START_HEX)) {
            hexNumStr = hexNumStr.substring(StaticValues.START_HEX.length());
        }
        return new BigDecimal(new BigInteger(hexNumStr, StaticValues.HEX));
    }

    public double getFormattedNumber(double value) {
        String formattedStr = StaticValues.DECIMAL_FORMAT.format(value);
        return Double.valueOf(formattedStr);
    }

    public String getFormattedNumberStr(BigDecimal value) {
        BigDecimal divided = value.divide(BigDecimal.valueOf(StaticValues.UNIT.getToWei()));
        return StaticValues.DECIMAL_FORMAT.format(divided);
    }

    public String getFormattedNumberStr(double value) {
        return StaticValues.DECIMAL_FORMAT.format(value);
    }

    public double getFormattedNumberDouble(BigDecimal value) {
        String formattedNumberStr = getFormattedNumberStr(value);
        return Double.valueOf(formattedNumberStr);
    }


    /**
     * 이더리움 출력 단위
     */
    public enum Unit {
        WEI("WEI", 1), GWEI("Gwei", 1_000_000_000);
        private String name;
        private long toWei;

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

        // Szabo	1000000000000	0.000001 // 만약 구현이 필요하다면 0숫자로 계산
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

