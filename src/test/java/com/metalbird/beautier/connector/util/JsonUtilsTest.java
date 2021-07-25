package com.metalbird.beautier.connector.util;

import com.metalbird.beautier.connector.model.BlockReqModel;
import com.metalbird.beautier.connector.model.BlockResModel;
import com.metalbird.beautier.connector.model.CustomConnectorException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class JsonUtilsTest {
    private JsonUtils jsonUtils = new JsonUtils();

    @Test
    public void getJsonStrFromReqModel() throws CustomConnectorException {
        String jsonStrFromReqModel = jsonUtils.getJsonStrFromReqModel(new BlockReqModel(""));
        Assert.assertTrue(jsonStrFromReqModel.length() > 0);
    }

    /**
     * resource 폴더의 샘플 데이터를 사용한다.
     * @throws CustomConnectorException
     * @throws IOException
     */
    @Test
    public void getBlockResModelFromJsonStr() throws CustomConnectorException, IOException {
        Path resourceDirectory = Paths.get("src","test", "resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        byte[] bytes = Files.readAllBytes(Paths.get(absolutePath + "/sample.json"));
        String json = new String(bytes);
        BlockResModel blockResModel = jsonUtils.getBlockResModelFromJsonStr(json);
        Assert.assertTrue(blockResModel.isSuccess());
    }
}