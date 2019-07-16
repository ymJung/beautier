package com.metalbird.beautier.connector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metalbird.beautier.connector.model.BlockReqModel;
import com.metalbird.beautier.connector.model.BlockResModel;
import com.metalbird.beautier.connector.model.CustomConnectorException;
import com.metalbird.beautier.connector.model.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class ExternalBlockConnector {
    private final int RETRY = 3;

    @Value("${value.network}")
    private String network;
    @Value("${value.privateKey}")
    private String privateKey;
    private final HttpHeaders httpHeaders;


    public ExternalBlockConnector() {
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    /**
     * external connector
     * @return
     * @throws CustomConnectorException
     */
    public BlockResModel getBlockResModel() throws CustomConnectorException {
        RestTemplate restTemplate = new RestTemplate();
        BlockReqModel blockReqModel = new BlockReqModel();
        return getBlockResModel(restTemplate, blockReqModel, 0);
    }

    private BlockResModel getBlockResModel(RestTemplate restTemplate, BlockReqModel blockReqModel, int callCount) throws CustomConnectorException {
        if (callCount >= RETRY) {
            throw new CustomConnectorException(CustomException.NETWORK_UNREACHABLE);
        }
        try {
            return restTemplate.postForObject(getBlockUrl(), getHttpEntityRequest(blockReqModel), BlockResModel.class);
        } catch (RestClientException e) {
            log.error("" , e); // error 처리
            return getBlockResModel(restTemplate, blockReqModel, ++callCount);
        } catch (Exception e) {
            log.error("unexpected exception. cause : ", e);
            BlockResModel blockResModel = new BlockResModel();
            blockResModel.setSuccess(false);
            return blockResModel;
        }
    }

    private HttpEntity<String> getHttpEntityRequest(BlockReqModel blockReqModel) throws CustomConnectorException {
        return new HttpEntity<>(getJsonByReqModel(blockReqModel), httpHeaders);
    }

    private String getBlockUrl() {
        return network + privateKey;
    }

    private String getJsonByReqModel(BlockReqModel blockReqModel) throws CustomConnectorException {
        try {
            return new ObjectMapper().writeValueAsString(blockReqModel);
        } catch (JsonProcessingException e) {
            log.error("json parse has error", e);
            throw new CustomConnectorException(CustomException.UNKNOWN);
        }

    }


}