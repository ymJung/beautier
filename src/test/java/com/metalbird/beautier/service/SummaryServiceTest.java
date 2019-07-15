package com.metalbird.beautier.service;

import com.metalbird.beautier.connector.ExternalBlockConnector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SummaryServiceTest {
    @InjectMocks
    private SummaryService summaryService;
    @Mock
    private ExternalBlockConnector connector;
    

    @Test
    public void getGasSummaryResultTest() {
        summaryService.getGasSummaryResult();
    }

}