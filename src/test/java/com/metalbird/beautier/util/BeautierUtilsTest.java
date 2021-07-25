package com.metalbird.beautier.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class BeautierUtilsTest {
    private BeautierUtils beautierUtils;
    private String hexStr;

    @Before
    public void setUp() {
        beautierUtils = new BeautierUtils();
        hexStr = "0xf7e48ce10";
    }

    @Test
    public void getHexToDouble() {
        double hexToDouble = beautierUtils.getHexToDouble(hexStr);
        Assert.assertEquals(String.valueOf(hexToDouble), "66.54321");
    }

    @Test
    public void getHexToLong() {
        long hexToLong = beautierUtils.getHexToLong(hexStr);
        Assert.assertEquals(hexToLong, 66543210000l);
    }

    @Test
    public void getFormattedTest() {
        double hexToDouble = beautierUtils.getHexToDouble(hexStr);
        double formatted = beautierUtils.getFormattedNumber(hexToDouble);
        Assert.assertEquals(String.valueOf(formatted), "66.5");
    }

    private int bitAndOperator(List<Integer> nums) {
        List<String> bits = new ArrayList<>();
        int maxLength = 0;

        for (Integer num : nums) {
            String e = Integer.toBinaryString(num);
            if (maxLength < e.length()) {
                maxLength = e.length();
            }
            bits.add(e);
        }
        List<String> padBits = new ArrayList<>();
        for (String bit : bits) {
            padBits.add(pad(bit, maxLength));
        }
        System.out.println(padBits);

        Map<Integer, List<String>> posPerBits = new LinkedHashMap<>();
        for (int i = 0; i < maxLength; i++) {
            posPerBits.put(i, getAndValue(padBits, i));
        }
        String res = "";
        for (Map.Entry<Integer, List<String>> entry : posPerBits.entrySet()) {
            if (isAllPositive(entry.getValue())) {
                res += "1";
            } else {
                res += "0";
            }
        }

        return Integer.parseInt(res, 2);
    }

    private boolean isAllPositive(List<String> value) {
        for (String s : value) {
            if (s.equals("0")) {
                return false;
            }
        }
        return true;
    }

    private List<String> getAndValue(List<String> padBits, int i) {
        List<String> res = new ArrayList<>();
        for (String padBit : padBits) {
            res.add(String.valueOf(padBit.charAt(i)));
        }
        return res;
    }

    private String pad(String input, int maxLength) {
        int padLen =  maxLength - input.length();
        if (padLen > 0) {
            for (int i = 0; i < padLen; i++) {
                input = ("0" + input);
            }
        }
        return input;
    }
}
