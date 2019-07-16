package com.metalbird.beautier.service;

import com.metalbird.beautier.connector.ExternalBlockConnector;

import com.metalbird.beautier.controller.model.CustomNetworkTimeoutException;
import com.metalbird.beautier.controller.model.CustomNetworkUnreachableException;
import com.metalbird.beautier.controller.model.CustomUnexpectedException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SummaryServiceTest {
    @InjectMocks
    private SummaryService summaryService;
    @Mock
    private ExternalBlockConnector connector;
    

    @Test
    public void getGasSummaryResultTest() throws Exception {
        summaryService.getGasSummaryResult();
    }


    @Test(expected = CustomNetworkUnreachableException.class)
    public void gasPriceHasNetworkErrorTest() throws Exception {
        Mockito.doThrow(CustomNetworkUnreachableException.class).when(connector.getBlockResModel());
        summaryService.getGasSummaryResult();
        Assert.fail();
    }

    @Test(expected = CustomNetworkTimeoutException.class)
    public void gasPriceHasNetworkTimeoutTest() throws Exception {
        Mockito.doThrow(CustomNetworkTimeoutException.class).when(connector.getBlockResModel());
        summaryService.getGasSummaryResult();
        Assert.fail();
    }

    @Test(expected = CustomUnexpectedException.class)
    public void gasPriceHasUnExpectedException() throws Exception {
        Mockito.doThrow(UnknownError.class).when(connector.getBlockResModel());
        summaryService.getGasSummaryResult();
        Assert.fail();
    }

}