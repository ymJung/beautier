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
}