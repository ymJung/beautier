package com.metalbird.beautier.connector.model;

import lombok.Data;

@Data
public class BlockResModel {
    private boolean success = true;
    private String jsonrpc;
    private long id;
    private BlockResult blockResult;
}