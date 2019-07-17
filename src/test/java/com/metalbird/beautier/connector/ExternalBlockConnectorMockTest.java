package com.metalbird.beautier.connector;

import com.metalbird.beautier.connector.model.BlockResModel;
import com.metalbird.beautier.connector.model.CustomConnectorException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(MockitoJUnitRunner.class)
public class ExternalBlockConnectorMockTest {
    @InjectMocks
    private ExternalBlockConnector externalBlockConnector;
    @Mock
    private RestTemplate restTemplate;




    @Test(expected = CustomConnectorException.class)
    public void getBlockResModel_RetryTest() throws CustomConnectorException {
        Mockito.doThrow(RestClientException.class).when(restTemplate).postForObject(Mockito.anyString(),
                Mockito.any(HttpEntity.class), Mockito.eq(String.class));
        externalBlockConnector.getBlockResModel();
        Assert.fail();
    }



}