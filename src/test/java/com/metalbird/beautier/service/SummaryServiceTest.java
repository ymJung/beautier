package com.metalbird.beautier.service;

import com.metalbird.beautier.connector.ExternalBlockConnector;

import com.metalbird.beautier.connector.model.BlockResModel;
import com.metalbird.beautier.connector.model.CustomConnectorException;
import com.metalbird.beautier.connector.model.CustomException;
import com.metalbird.beautier.connector.util.JsonUtils;
import com.metalbird.beautier.controller.model.BlockSummaryResult;
import com.metalbird.beautier.controller.model.SummaryResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(MockitoJUnitRunner.class)
public class SummaryServiceTest {
    @InjectMocks
    private SummaryService summaryService;
    @Mock
    private ExternalBlockConnector connector;
    
    private BlockResModel getBlockResSample() throws IOException, CustomConnectorException {
        Path resourceDirectory = Paths.get("src","test", "resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        byte[] bytes = Files.readAllBytes(Paths.get(absolutePath + "/sample.json"));
        String json = new String(bytes);
        return new JsonUtils().getBlockResModelFromJsonStr(json);
    }

    @Before
    public void setUp() throws Exception {
        Mockito.when(connector.getBlockResModel()).thenReturn(getBlockResSample());
    }

    @Test
    public void getGasSummaryResultTest() throws Exception {
        SummaryResult result = summaryService.getGasSummaryResult();
        Assert.assertTrue(result.isSuccess());
    }

    @Test(expected = CustomConnectorException.class)
    public void getGasSummaryResultTest_BlockIsNull() throws Exception {
        Mockito.when(connector.getBlockResModel()).thenReturn(null);
        SummaryResult result = summaryService.getGasSummaryResult();
        Assert.assertTrue(result.isSuccess());
    }


    @Test(expected = CustomConnectorException.class)
    public void gasPriceHasNetworkErrorTest() throws Exception {
        Mockito.doThrow(new CustomConnectorException(CustomException.NETWORK_UNREACHABLE)).when(connector).getBlockResModel();
        SummaryResult gasSummaryResult = summaryService.getGasSummaryResult();
        Assert.assertFalse(gasSummaryResult.isSuccess());
    }

    @Test(expected = CustomConnectorException.class)
    public void gasPriceHasNetworkTimeoutTest() throws Exception {
        Mockito.doThrow(new CustomConnectorException(CustomException.NETWORK_TIMEOUT)).when(connector).getBlockResModel();
        SummaryResult gasSummaryResult = summaryService.getGasSummaryResult();
        Assert.assertFalse(gasSummaryResult.isSuccess());
    }

    @Test(expected = CustomConnectorException.class)
    public void gasPriceHasUnExpectedException() throws Exception {
        Mockito.doThrow(new CustomConnectorException(CustomException.UNKNOWN)).when(connector).getBlockResModel();
        SummaryResult gasSummaryResult = summaryService.getGasSummaryResult();
        Assert.assertFalse(gasSummaryResult.isSuccess());
    }


    @Test
    public void getBlockSummaryResultByBlockResTest() throws IOException, CustomConnectorException {
        BlockResModel sampleBlockResModel = getBlockResSample();
        BlockSummaryResult result = summaryService.getBlockSummaryResultByBlockRes(sampleBlockResModel.getResult());
        Assert.assertNotNull(result);
        Assert.assertTrue(result.getTxBlockCount() > 0);
        Assert.assertEquals(1, (int) result.getOrderedPriceTxCntMap().get(Double.valueOf(0)));


    }

   


}