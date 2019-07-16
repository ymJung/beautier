package com.metalbird.beautier.connector;

import com.metalbird.BeautierApplication;
import com.metalbird.beautier.connector.model.BlockResModel;
import com.metalbird.beautier.connector.model.CustomConnectorException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExternalBlockConnectorTest {
    @Autowired
    private ExternalBlockConnector externalBlockConnector;

    @Before
    public void setUp() {
        externalBlockConnector = new ExternalBlockConnector();
    }

    @Test
    public void getBlockResModelTest() throws CustomConnectorException {
        BlockResModel blockResModel = externalBlockConnector.getBlockResModel();
        Assert.assertTrue(blockResModel.isSuccess());
        Assert.assertNotNull(blockModel.getBlockResModel());
    }

   


    

}