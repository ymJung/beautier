package com.metalbird.beautier.service;

import java.util.*;
import java.util.stream.Collectors;

import com.metalbird.beautier.connector.ExternalBlockConnector;
import com.metalbird.beautier.connector.model.BlockResModel;
import com.metalbird.beautier.connector.model.BlockResult;
import com.metalbird.beautier.connector.model.CustomConnectorException;
import com.metalbird.beautier.connector.model.CustomException;
import com.metalbird.beautier.controller.model.BeautierOrder;
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
	 * @param beautierOrder
	 * @return
	 * @throws Exception
	 */
	public SummaryResult getGasSummaryResult(BeautierOrder beautierOrder) throws Exception {

		BlockResModel blockResModel = connector.getBlockResModel();
		checkBlockResModel(blockResModel);
		BlockSummaryResult blockSummaryResult = getBlockSummaryResultByBlockRes(beautierOrder, blockResModel.getResult());
		return new SummaryResult(blockSummaryResult);
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
	public BlockSummaryResult getBlockSummaryResultByBlockRes(BeautierOrder order, BlockResult blockResult) {
		List<Double> priceList = blockResult.getTransactions().stream()
				.map(each -> beautierUtils.getHexToDouble(each.getGasPrice()))
				.collect(Collectors.toList());

		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		Map<Double, Integer> orderedPriceTxCntMap = order.getOrderedMap();
		for (double gasPriceDouble : priceList) {
		    double formattedPrice = beautierUtils.getFormattedNumber(gasPriceDouble);
			orderedPriceTxCntMap.putIfAbsent(formattedPrice, 0);
			orderedPriceTxCntMap.put(formattedPrice, orderedPriceTxCntMap.get(formattedPrice) + 1);
			if (formattedPrice > max) {
				max = formattedPrice;
			}
			if (formattedPrice < min) {
				min = formattedPrice;
			}
		}
		double avg = beautierUtils.getFormattedNumber(priceList.stream().mapToDouble(each -> each).average().getAsDouble());
		long blockNumber = beautierUtils.getHexToLong(blockResult.getNumber());
		long txCnt = blockResult.getTransactions().size(); // tx cnt

		return BlockSummaryResult.builder()
			.orderedPriceTxCntMap(orderedPriceTxCntMap)
			.averageGasPrice(avg).maxGasPrice(max).minGasPrice(min)
			.newestBlockNumber(blockNumber)
			.txBlockCount(txCnt)
			.build();
	}
}