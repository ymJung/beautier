package com.metalbird.beautier.connector.util;

import com.google.gson.Gson;
import com.metalbird.beautier.connector.model.BlockReqModel;
import com.metalbird.beautier.connector.model.BlockResModel;
import com.metalbird.beautier.connector.model.CustomConnectorException;
import com.metalbird.beautier.connector.model.CustomException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtils {

    public String getJsonStrFromReqModel(BlockReqModel blockReqModel) throws CustomConnectorException {
        try {
            Gson gson = new Gson();
            return gson.toJson(blockReqModel);
        } catch (Exception e) {
            log.error("model to json parse has error", e);
            throw new CustomConnectorException(CustomException.UNKNOWN);
        }

    }

    public BlockResModel getBlockResModelFromJsonStr(String res) throws CustomConnectorException {
        try {
            return new Gson().fromJson(res, BlockResModel.class);
        } catch (Exception e) {
            log.error("json to model parse has error", e);
            throw new CustomConnectorException(CustomException.UNKNOWN);
        }
    }
}