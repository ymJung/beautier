package com.metalbird.beautier.service;

import com.metalbird.beautier.connector.ExternalBlockConnector;
import com.metalbird.beautier.controller.model.SummaryResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SummaryService {

    @Autowired
    private ExternalBlockConnector connector;

	public SummaryResult getGasSummaryResult() {
		return null;
	}

}