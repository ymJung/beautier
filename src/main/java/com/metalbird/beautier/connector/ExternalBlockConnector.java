package com.metalbird.beautier.connector;

import javax.annotation.PostConstruct;

import com.google.gson.Gson;
import com.metalbird.beautier.connector.model.*;
import com.metalbird.beautier.connector.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private RestTemplate restTemplate;
    private JsonUtils jsonUtils;



    @PostConstruct
    public void init() {
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        network = "https://mainnet.infura.io/v3/";
        privateKey = "edd64aeb57e54756b89140933a6c715d";
        jsonUtils = new JsonUtils();
    }
   

    /**
     * external connector
     * @return
     * @throws CustomConnectorException
     */
    public BlockResModel getBlockResModel() throws CustomConnectorException {        
        BlockReqModel blockReqModel = new BlockReqModel();
        return getBlockResModel(blockReqModel, 0);
    }

    private BlockResModel getBlockResModel(BlockReqModel blockReqModel, int callCount) throws CustomConnectorException {
        if (callCount >= RETRY) {
            throw new CustomConnectorException(CustomException.NETWORK_UNREACHABLE);
        }
        try {
            String res = restTemplate.postForObject(getBlockUrl(), getHttpEntityRequest(blockReqModel), String.class);
            return jsonUtils.getBlockResModelFromJsonStr(res);
        } catch (RestClientException e) {
            log.error("RestClientException. detail : " , e); // error 처리
            return getBlockResModel(blockReqModel, ++callCount);
        } catch (Exception e) {
            log.error("unexpected exception. cause : ", e);
            BlockResModel blockResModel = new BlockResModel();
            blockResModel.setFail();
            return blockResModel;
        }
    }

    private HttpEntity<String> getHttpEntityRequest(BlockReqModel blockReqModel) throws CustomConnectorException {
        return new HttpEntity<>(jsonUtils.getJsonStrFromReqModel(blockReqModel), httpHeaders);
    }

    private String getBlockUrl() {
        return network + privateKey;
    }



}