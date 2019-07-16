package com.metalbird.beautier.controller;

import com.metalbird.beautier.controller.model.CustomNetworkUnreachableException;
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
    public void gasPriceHasNetworkErrorTest() throws Exception {
        Mockito.doThrow(CustomNetworkUnreachableException.class).when(summaryService).getGasSummaryResult();
        SummaryResult result = summaryController.gasPrice();
        Assert.assertFalse(result.isSuccess());
    }


}