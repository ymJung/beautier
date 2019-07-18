package com.metalbird.beautier.connector.model;

import lombok.Data;

import java.util.*;


@Data
public class BlockResult {
    private String difficulty;
    private String extraData;
    private String gasLimit;
    private String gasUsed;
    private String hash;
    private String logsBloom;
    private String miner;
    private String mixHash;
    private String nonce;
    private String number; 
    private String parentHash;
    private String receiptsRoot;
    private String sha3Uncles;
    private String size;
    private String stateRoot;
    private String timestamp;
    private String totalDifficulty;
    private List<BlockTxModel> transactions;
    private String transactionsRoot;
    private List<String> uncles;
}