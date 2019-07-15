package com.metalbird.beautier.controller;

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
    public void gasPriceTest() {
        SummaryResult summaryResult = new SummaryResult();
        Mockito.when(summaryService.getGasSummaryResult()).thenReturn(summaryResult);
        SummaryResult result = summaryController.gasPrice();
        Assert.assertEquals(result, summaryResult);
        
    }

    
}