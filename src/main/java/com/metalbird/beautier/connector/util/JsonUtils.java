package com.metalbird.beautier.connector.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metalbird.beautier.connector.model.CustomConnectorException;
import com.metalbird.beautier.connector.model.CustomException;

public class JsonUtils {
    public String getJsonByReqModel(Object object) throws CustomConnectorException {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new CustomConnectorException(CustomException.UNKNOWN);
        }

    }
}