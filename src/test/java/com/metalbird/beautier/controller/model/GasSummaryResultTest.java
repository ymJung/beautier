package com.metalbird.beautier.controller.model;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Assert;


@RunWith(MockitoJUnitRunner.class)
public class GasSummaryResultTest {
    private GasSummaryResult gasSummaryResult;
    private final long UNIT_NUM = 1_000_000_000l;
    @Before
    public void setUp() {
        gasSummaryResult = new GasSummaryResult(getBigNumber(1000), getBigNumber(1500), getBigNumber(500));
    }
    private BigInteger getBigNumber(int num) {
        long longNum = num * UNIT_NUM;
        return BigInteger.valueOf(longNum);
    }

    @Test
    public void getAvgPriceTest() {
        Assert.assertEquals(gasSummaryResult.getAvgPrice(), "10.00");
    }

    @Test
    public void getMaxPriceTest() {        
        Assert.assertEquals(gasSummaryResult.getMaxPrice(), "15.00");
    }

    @Test
    public void getMinPriceTest() {
        Assert.assertEquals(gasSummaryResult.getMinPrice(), "5.00");
    }
}