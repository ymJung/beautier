package com.metalbird.beautier.connector.model;

import java.util.*;

import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class BlockReqModel {
    private String jsonrpc;
    private String method;
    private long id;
    private List<Object> params;

    public BlockReqModel(String blockNumberStr) {
        if (StringUtils.isEmpty(blockNumberStr)) {
            blockNumberStr = "latest";
        }
        jsonrpc = "2.0";
        method = "eth_getBlockByNumber";
        id = 1;
        params = new ArrayList<>();
        params.add(blockNumberStr);
        params.add(true); //full transactions - 트렌젝션 내부정보까지 포함할지 여부
    }
   
}