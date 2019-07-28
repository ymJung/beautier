package com.metalbird.beautier.controller.model;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

@Getter
public enum BeautierOrder {
    ASC{
            @Override
            public Map<String, Integer> getOrderedMap(Map<String, Integer> map) {
                return new TreeMap<>(map);
            }

    }, DESC{
            @Override
            public Map<String, Integer> getOrderedMap(Map<String, Integer> map) {
                TreeMap<String, Integer> reverseOrderMap= new TreeMap<>(Collections.reverseOrder());
                reverseOrderMap.putAll(map);
                return reverseOrderMap;
            }
    };

    public Map<String, Integer> getOrderedMap(Map<String, Integer> map) {
        return new TreeMap<>(); //default
    }
}
