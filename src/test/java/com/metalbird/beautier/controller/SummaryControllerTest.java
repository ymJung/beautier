package com.metalbird.beautier.controller;

import com.metalbird.beautier.connector.model.CustomConnectorException;
import com.metalbird.beautier.connector.model.CustomException;
import com.metalbird.beautier.controller.model.BeautierOrder;
import com.metalbird.beautier.controller.model.SummaryResult;
import com.metalbird.beautier.service.SummaryService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;



@RunWith(MockitoJUnitRunner.class)
public class SummaryControllerTest {

    @InjectMocks
    private SummaryController summaryController;

    @Mock
    private SummaryService summaryService;

    private String order;
    private BeautierOrder beautierOrder;
    private String blockNumStr;

    @Before
    public void setUp() {
        order = "DESC";
        beautierOrder = BeautierOrder.valueOf(order);
        blockNumStr = "0xFF";
    }

        
    @Test
    public void gasPriceTest() throws Exception {
        SummaryResult summaryResult = new SummaryResult(true, "OK");
        Mockito.when(summaryService.getGasSummaryResult(beautierOrder, blockNumStr)).thenReturn(summaryResult);
        SummaryResult result = summaryController.gasPrice(order, blockNumStr);
        Assert.assertEquals(result, summaryResult);
    }

    @Test
    public void gasPriceTest_HasCusExceptionError() throws Exception {
        Mockito.doThrow(new CustomConnectorException(CustomException.UNKNOWN)).when(summaryService).getGasSummaryResult(Mockito.any(BeautierOrder.class), Mockito.anyString());
        SummaryResult summaryResult = summaryController.gasPrice(order, blockNumStr);
        Assert.assertFalse(summaryResult.isSuccess());
    }

    @Test
    public void gasPriceTest_HasUnexpectedExceptionError() throws Exception {
        Mockito.doThrow(UnsupportedOperationException.class).when(summaryService).getGasSummaryResult(Mockito.any(BeautierOrder.class), Mockito.anyString());
        SummaryResult summaryResult = summaryController.gasPrice(order, blockNumStr);
        Assert.assertFalse(summaryResult.isSuccess());
    }

    @Test
    public void gasPriceTest_invalidOrderParam() {
        SummaryResult summaryResult = summaryController.gasPrice("invalid", "");
        Assert.assertFalse(summaryResult.isSuccess());
    }

    @Test
    public void gasPricePretty() throws Exception {
        SummaryResult summaryResult = new SummaryResult(true, "OK");
        Mockito.when(summaryService.getGasSummaryResult(beautierOrder, blockNumStr)).thenReturn(summaryResult);
        String result = summaryController.gasPricePretty(order, blockNumStr);
        Assert.assertTrue(result.contains("\n"));
    }

}