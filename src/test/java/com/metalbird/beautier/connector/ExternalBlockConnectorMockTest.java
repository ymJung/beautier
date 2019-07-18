package com.metalbird.beautier.connector;

import com.metalbird.beautier.connector.model.BlockResModel;
import com.metalbird.beautier.connector.model.CustomConnectorException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@RunWith(MockitoJUnitRunner.class)
public class ExternalBlockConnectorMockTest {
    @InjectMocks
    private ExternalBlockConnector externalBlockConnector;
    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        externalBlockConnector.init();
    }



    @Test(expected = CustomConnectorException.class)
    public void getBlockResModelTest_Retry() throws CustomConnectorException {
        Mockito.doThrow(RestClientException.class).when(restTemplate).postForObject(Mockito.anyString(),
                Mockito.any(HttpEntity.class), Mockito.eq(String.class));
        externalBlockConnector.getBlockResModel();
        Assert.fail();
    }

    @Test
    public void getBlockResModelTest_UnexpectedException() throws CustomConnectorException {
        Mockito.doThrow(UnsupportedOperationException.class).when(restTemplate).postForObject(Mockito.anyString(),
                Mockito.any(HttpEntity.class), Mockito.eq(String.class));
        BlockResModel blockResModel = externalBlockConnector.getBlockResModel();
        Assert.assertFalse(blockResModel.isSuccess());
    }

    @Test
    public void getBlockResModelTest() throws CustomConnectorException {
        String responseStr = "{success:true}";
        Mockito.when(restTemplate.postForObject(Mockito.anyString(), Mockito.any(HttpEntity.class), Mockito.eq(String.class)))
                .thenReturn(responseStr);
        BlockResModel blockResModel = externalBlockConnector.getBlockResModel();
        Assert.assertTrue(blockResModel.isSuccess());

    }



}