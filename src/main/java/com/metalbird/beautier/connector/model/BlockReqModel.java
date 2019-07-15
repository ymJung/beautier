package com.metalbird.beautier.controller.model;

import java.util.*;

import lombok.Data;
@Data
public class BlockReqModel {

    private String jsonrpc = "2.0";
    private String method = "eth_getBlockByNumber";
    private long id = 1;
    private List<ReqParam> params;
    
    private class ReqParam {
        private boolean latest = true;
    }
}