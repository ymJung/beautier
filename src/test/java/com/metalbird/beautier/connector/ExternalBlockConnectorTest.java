package com.metalbird.beautier.connector;

import com.metalbird.beautier.connector.model.BlockResModel;
import com.metalbird.beautier.connector.model.CustomConnectorException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 인터넷 연결 환경이 필요
 * SpringRunner 를 사용하여 실제 환경과 동일하게 동작하게 함으로
 * 구동에 시간이 다소 걸림
 */
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
        Assert.assertNotNull(blockResModel.getResult());
    }


    @Test
    public void getBlockResModel_RetryTest() throws CustomConnectorException {

        BlockResModel blockResModel = externalBlockConnector.getBlockResModel();
        Assert.assertTrue(blockResModel.isSuccess());
        Assert.assertNotNull(blockResModel.getResult());
    }


    

}