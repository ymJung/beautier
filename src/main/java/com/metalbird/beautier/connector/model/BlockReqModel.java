package com.metalbird.beautier.connector.model;

import java.util.*;

import lombok.Data;
@Data
public class BlockReqModel {

    private String jsonrpc = "2.0";
    private String method = "eth_getBlockByNumber";
    private long id = 1;
    private List<Object> params;

    public BlockReqModel() {
        jsonrpc = "2.0";
        method = "eth_getBlockByNumber";
        id = 1;
        params = new ArrayList<>();
        params.add("latest");
        params.add(true);
    }
   
}