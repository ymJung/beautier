package com.metalbird.beautier.service;

import java.util.*;
import java.util.stream.Collectors;

import com.metalbird.beautier.connector.ExternalBlockConnector;
import com.metalbird.beautier.connector.model.BlockResModel;
import com.metalbird.beautier.connector.model.BlockResult;
import com.metalbird.beautier.connector.model.CustomConnectorException;
import com.metalbird.beautier.connector.model.CustomException;
import com.metalbird.beautier.controller.model.BlockSummaryResult;
import com.metalbird.beautier.controller.model.SummaryResult;

import com.metalbird.beautier.util.BeautierUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class SummaryService {

    @Autowired
    private ExternalBlockConnector connector;

    private BeautierUtils beautierUtils = new BeautierUtils();

	/**
	 * connector를 통해 가져온 block 가공
	 * @return
	 * @throws Exception
	 */
	public SummaryResult getGasSummaryResult() throws Exception {
		BlockResModel blockResModel = connector.getBlockResModel();
		checkBlockResModel(blockResModel);
		BlockSummaryResult blockSummaryResult = getBlockSummaryResultByBlockRes(blockResModel.getResult());
		SummaryResult summaryResult = new SummaryResult();
		summaryResult.setBlockSummaryResult(blockSummaryResult);
		return summaryResult;
	}

	/**
	 * check block res model.
	 * 만약 에러가 있다면 에러 코드로 던져준다.
	 * @param blockResModel
	 * @return
	 * @throws CustomConnectorException
	 */
	private void checkBlockResModel(BlockResModel blockResModel) throws CustomConnectorException {
	    if (blockResModel == null || blockResModel.getResult() == null) {
	        throw new CustomConnectorException(CustomException.NULL_BLOCK);
		}
	    if (CollectionUtils.isEmpty(blockResModel.getResult().getTransactions())) {
			throw new CustomConnectorException(CustomException.NULL_TRANSACTIONS);
		}
	}

	/**
	 * 최신 블록의 Block Number (10 진수로)
	 * 블록의 트랜잭션 갯수
	 * 트랜잭션의 gas price 평균값,최대값,최소값 (Gwei 단위로 소숫점 이하 1 자리까지)
	 * 트랜잭션의 gas price 를 오름차순 / 트랜잭션 수 표시 / gas price 로 그룹핑 가격순 정렬
	 * 
	 * @param blockResult
	 * @return
	 */
	public BlockSummaryResult getBlockSummaryResultByBlockRes(BlockResult blockResult) {
		Map<Double, Integer> orderedPriceTxCntMap = new TreeMap<>(Collections.reverseOrder());
		List<Double> priceList = blockResult.getTransactions().stream().map(
				each -> beautierUtils.getHexToDouble(each.getGasPrice())).collect(Collectors.toList());

		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		for (Double gasPriceDouble: priceList) {
			orderedPriceTxCntMap.putIfAbsent(gasPriceDouble, 0);
			orderedPriceTxCntMap.put(gasPriceDouble, orderedPriceTxCntMap.get(gasPriceDouble) + 1);
			if (gasPriceDouble > max) {
				max = gasPriceDouble;
			}
			if (gasPriceDouble < min) {
				min = gasPriceDouble;
			}
		}
		double avg = priceList.stream().mapToDouble(each -> each).average().getAsDouble();
		long blockNumber = beautierUtils.getHexToLong(blockResult.getNumber());
		long txCnt = blockResult.getTransactions().size(); // tx cnt

		return BlockSummaryResult.builder()
			.orderedPriceTxCntMap(orderedPriceTxCntMap)
			.avgPrice(avg).maxPrice(max).minPrice(min)
			.newestBlockNumber(blockNumber)
			.txBlockCount(txCnt)
			.build();
	}
}