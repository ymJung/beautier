package com.metalbird.beautier.controller;

import com.metalbird.beautier.connector.model.CustomConnectorException;
import com.metalbird.beautier.connector.model.CustomException;
import com.metalbird.beautier.controller.model.SummaryResult;
import com.metalbird.beautier.service.SummaryService;

import org.junit.Assert;
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

        
    @Test
    public void gasPriceTest() throws Exception {
        SummaryResult summaryResult = new SummaryResult(true, "OK");
        Mockito.when(summaryService.getGasSummaryResult()).thenReturn(summaryResult);
        SummaryResult result = summaryController.gasPrice();
        Assert.assertEquals(result, summaryResult);
    }

    @Test
    public void gasPriceHasErrorTest() throws Exception {
        Mockito.doThrow(new CustomConnectorException(CustomException.UNKNOWN)).when(summaryService).getGasSummaryResult();
        summaryController.gasPrice();
        Assert.fail();
    }


}