package com.metalbird.beautier.service;

import com.metalbird.beautier.connector.ExternalBlockConnector;

import com.metalbird.beautier.connector.model.BlockResModel;
import com.metalbird.beautier.connector.model.CustomConnectorException;
import com.metalbird.beautier.connector.model.CustomException;
import com.metalbird.beautier.connector.util.JsonUtils;
import com.metalbird.beautier.controller.model.BeautierOrder;
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
    private BeautierOrder beautierOrder;
    private String blockStr;
    private boolean fullTx;

    private BlockResModel getBlockResSample() throws IOException, CustomConnectorException {
        Path resourceDirectory = Paths.get("src","test", "resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        byte[] bytes = Files.readAllBytes(Paths.get(absolutePath + "/sample.json"));
        String json = new String(bytes);
        return new JsonUtils().getBlockResModelFromJsonStr(json);
    }

    @Before
    public void setUp() throws Exception {
        beautierOrder = BeautierOrder.DESC;
        blockStr = "0xFF";
        fullTx = true;
        Mockito.when(connector.getBlockResModelUseParams(blockStr)).thenReturn(getBlockResSample());
    }

    @Test
    public void getGasSummaryResultTest() throws Exception {
        SummaryResult result = summaryService.getGasSummaryResult(beautierOrder, blockStr);
        Assert.assertTrue(result.isSuccess());
    }

    @Test(expected = CustomConnectorException.class)
    public void getGasSummaryResultTest_BlockIsNull() throws Exception {
        Mockito.when(connector.getBlockResModelUseParams(blockStr)).thenReturn(null);
        SummaryResult result = summaryService.getGasSummaryResult(beautierOrder, blockStr);
        Assert.assertTrue(result.isSuccess());
    }


    @Test(expected = CustomConnectorException.class)
    public void gasPriceHasNetworkErrorTest() throws Exception {
        Mockito.doThrow(new CustomConnectorException(CustomException.NETWORK_UNREACHABLE)).when(connector).getBlockResModelUseParams(blockStr);
        SummaryResult gasSummaryResult = summaryService.getGasSummaryResult(beautierOrder, blockStr);
        Assert.assertFalse(gasSummaryResult.isSuccess());
    }

    @Test(expected = CustomConnectorException.class)
    public void gasPriceHasNetworkTimeoutTest() throws Exception {
        Mockito.doThrow(new CustomConnectorException(CustomException.NETWORK_TIMEOUT)).when(connector).getBlockResModelUseParams(blockStr);
        SummaryResult gasSummaryResult = summaryService.getGasSummaryResult(beautierOrder, blockStr);
        Assert.assertFalse(gasSummaryResult.isSuccess());
    }

    @Test(expected = CustomConnectorException.class)
    public void gasPriceHasUnExpectedException() throws Exception {
        Mockito.doThrow(new CustomConnectorException(CustomException.UNKNOWN)).when(connector).getBlockResModelUseParams(blockStr);
        SummaryResult gasSummaryResult = summaryService.getGasSummaryResult(beautierOrder, blockStr);
        Assert.assertFalse(gasSummaryResult.isSuccess());
    }


    @Test
    public void getBlockSummaryResultByBlockResTest() throws IOException, CustomConnectorException {
        BlockResModel sampleBlockResModel = getBlockResSample();
        BlockSummaryResult result = summaryService.getBlockSummaryResultByBlockRes(BeautierOrder.DESC, sampleBlockResModel.getResult());
        Assert.assertNotNull(result);
        Assert.assertTrue(result.getTxBlockCount() > 0);
        Assert.assertEquals(1, (int) result.getOrderedPriceTxCntMap().get("0"));


    }

    @Test(expected = CustomConnectorException.class)
    public void getGasSummaryResultTest_hasParamError() throws Exception {
        summaryService.getGasSummaryResult(beautierOrder, "invalid");
        Assert.fail();

    }



}