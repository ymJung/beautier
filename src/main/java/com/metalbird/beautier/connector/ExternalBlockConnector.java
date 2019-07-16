package com.metalbird.beautier.connector;

import com.metalbird.beautier.connector.model.BlockResModel;
import com.metalbird.beautier.controller.model.CustomNetworkTimeoutException;
import com.metalbird.beautier.controller.model.CustomNetworkUnreachableException;
import com.metalbird.beautier.controller.model.CustomUnexpectedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ExternalBlockConnector {
    private final int RETRY = 3;

    @Value("#{value.network}")
    private String network;
    @Value("#{value.privateKey}")
    private String privateKey;

    /**
     * external connector
     * @return
     * @throws CustomNetworkUnreachableException
     * @throws CustomNetworkTimeoutException
     * @throws CustomUnexpectedException
     */
    public BlockResModel getBlockResModel() throws CustomNetworkUnreachableException, CustomNetworkTimeoutException, CustomUnexpectedException {
        return null;
    }
}