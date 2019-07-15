package com.metalbird.beautier.connector.model;

import lombok.Data;

@Data
public class BlockTxModel {
    private String blockHash;
    private String blockNumber;
    private String from;
    private String gas;
    private String gasPrice;
    private String hash;
    private String input;
    private String nonce;
    private String value;

    private String to;
    private String transactionIndex;
    private String r;
    private String s;
    private String v;
}