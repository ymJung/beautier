package com.metalbird.beautier.controller.model;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

@Getter
public enum BeautierOrder {
    ASC{
            @Override
            public Map<Double, Integer> getOrderedMap() {
                return new TreeMap<>();
            }

    }, DESC{
            @Override
            public Map<Double, Integer> getOrderedMap() {
                return new TreeMap<>(Collections.reverseOrder());
            }
    };

    public Map<Double, Integer> getOrderedMap() {
        return new TreeMap<>(); //default
    }
}
