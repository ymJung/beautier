package com.metalbird.beautier.connector;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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

    @Value("${network.url}")
    private String network;
    @Value("${network.key}")
    private String privateKey;
    private HttpHeaders httpHeaders;


    @PostConstruct
    public void init() {
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        network = "";
        privateKey = "";

    }
   

    /**
     * external connector
     * @return
     * @throws CustomConnectorException
     */
    public BlockResModel getBlockResModel() throws CustomConnectorException {        
        init();
        RestTemplate restTemplate = new RestTemplate();
        BlockReqModel blockReqModel = new BlockReqModel();
        return getBlockResModel(restTemplate, blockReqModel, 0);
    }

    private BlockResModel getBlockResModel(RestTemplate restTemplate, BlockReqModel blockReqModel, int callCount) throws CustomConnectorException {
        if (callCount >= RETRY) {
            throw new CustomConnectorException(CustomException.NETWORK_UNREACHABLE);
        }
        try {
            String res = restTemplate.postForObject(getBlockUrl(), getHttpEntityRequest(blockReqModel), String.class);
            BlockResModel result = getBlockResModelFromJsonStr(res);
            return result;
        } catch (RestClientException e) {
            log.error("RestClientException. detail : " , e); // error 처리
            return getBlockResModel(restTemplate, blockReqModel, ++callCount);
        } catch (Exception e) {
            log.error("unexpected exception. cause : ", e);
            BlockResModel blockResModel = new BlockResModel();
            blockResModel.setFail();
            return blockResModel;
        }
    }
    private BlockResModel getBlockResModelFromJsonStr(String res) {
        Gson gson = new Gson();
        BlockResModel transform = gson.fromJson(res, BlockResModel.class);
        return transform;
    }

    private HttpEntity<String> getHttpEntityRequest(BlockReqModel blockReqModel) throws CustomConnectorException {
        return new HttpEntity<>(getJsonByReqModel(blockReqModel), httpHeaders);
    }

    private String getBlockUrl() {
        return network + privateKey;
    }

    private String getJsonByReqModel(BlockReqModel blockReqModel) throws CustomConnectorException {
        try {
            Gson gson = new Gson();
            return gson.toJson(blockReqModel);
        } catch (Exception e) {
            log.error("json parse has error", e);
            throw new CustomConnectorException(CustomException.UNKNOWN);
        }

    }


}