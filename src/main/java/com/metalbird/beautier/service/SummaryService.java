package com.metalbird.beautier.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.metalbird.beautier.connector.ExternalBlockConnector;
import com.metalbird.beautier.connector.model.BlockResModel;
import com.metalbird.beautier.connector.model.BlockResult;
import com.metalbird.beautier.connector.model.BlockTxModel;
import com.metalbird.beautier.controller.model.BlockSummaryResult;
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
		BlockSummaryResult blockSummaryResult = getBlockSummaryResultByBlockRes(blockResModel.getResult());
		SummaryResult summaryResult = new SummaryResult();
		summaryResult.setBlockSummaryResult(blockSummaryResult);
		return summaryResult;
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
		
		Map<BigInteger, Integer> orderedPriceTxCntMap = new TreeMap<>();
		for (BlockTxModel txModel : blockResult.getTransactions()) {
			BigInteger price = new BigInteger(txModel.getGasPrice());
			Integer cnt = orderedPriceTxCntMap.get(price);
			if (cnt == null) {
				orderedPriceTxCntMap.put(price, 0);
			}
			orderedPriceTxCntMap.put(price, orderedPriceTxCntMap.get(price) + 1);
		}
		double avg = blockResult.getTransactions().stream().mapToDouble(BlockTxModel::getGasPriceDouble).average().getAsDouble();
		double max = blockResult.getTransactions().stream().mapToDouble(BlockTxModel::getGasPriceDouble).max().getAsDouble();
		double min = blockResult.getTransactions().stream().mapToDouble(BlockTxModel::getGasPriceDouble).min().getAsDouble();
		long blockNumber = Long.parseLong(blockResult.getNumber(), 16); // block number
		long txCnt = blockResult.getTransactions().size(); // tx cnt

		BlockSummaryResult result = BlockSummaryResult.builder()
			.orderedPriceTxCntMap(orderedPriceTxCntMap)
			.avgPrice(avg).maxPrice(max).minPrice(min)
			.newestBlockNumber(blockNumber)
			.txBlockCount(txCnt)
			.build();
		return result;
	}
}