package com.metalbird.beautier.connector;

import javax.annotation.PostConstruct;

import com.metalbird.beautier.connector.model.*;
import com.metalbird.beautier.connector.util.JsonUtils;
import com.metalbird.beautier.util.StaticValues;
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
    private HttpHeaders httpHeaders;
    private JsonUtils jsonUtils;

    @Value("${network.url}")
    private String network;
    @Value("${network.key}")
    private String privateKey;
    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        jsonUtils = new JsonUtils();
    }
   

    /**
     * get block res model by block number.
     * @param blockNumberStr
     * @return
     * @throws CustomConnectorException
     */
    public BlockResModel getBlockResModelUseParams(String blockNumberStr) throws CustomConnectorException {
        BlockReqModel blockReqModel = new BlockReqModel(blockNumberStr);
        return getBlockResModel(blockReqModel, 0);
    }

    private BlockResModel getBlockResModel(BlockReqModel blockReqModel, int callCount) throws CustomConnectorException {
        if (callCount >= StaticValues.RETRY) {
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