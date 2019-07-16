package com.metalbird.beautier.service;

import com.metalbird.beautier.connector.ExternalBlockConnector;
import com.metalbird.beautier.connector.model.BlockResModel;
import com.metalbird.beautier.controller.model.GasSummaryResult;
import com.metalbird.beautier.controller.model.SummaryResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SummaryService {

    @Autowired
    private ExternalBlockConnector connector;

	/**
	 * connector를 통해 가져온 block 가공
	 * @return
	 * @throws Exception
	 */
	public SummaryResult getGasSummaryResult() throws Exception {
		BlockResModel blockResModel = connector.getBlockResModel();
		SummaryResult summaryResult = new SummaryResult();
		// logic avg max min
//		GasSummaryResult gasSummaryResult = new GasSummaryResult(avg, max, min);
//		summaryResult.setGasSummaryResult(gasSummaryResult);
		return summaryResult;
	}

}