package com.metalbird.beautier.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
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
import com.metalbird.beautier.util.StaticValues;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class SummaryService {

    @Autowired
    private ExternalBlockConnector connector;

    private BeautierUtils beautierUtils = new BeautierUtils();


	/**
	 * connector를 통해 가져온 block 가공
	 * @param beautierOrder
     * @param blockNumberStr
	 * @return
	 * @throws Exception
	 */
	public SummaryResult getGasSummaryResult(BeautierOrder beautierOrder, String blockNumberStr) throws Exception {
		BlockResModel blockResModel = connector.getBlockResModelUseParams(getBlockNumberStr(blockNumberStr));
		checkBlockResModel(blockResModel);
		BlockSummaryResult blockSummaryResult = getBlockSummaryResultByBlockRes(beautierOrder, blockResModel.getResult());
		return new SummaryResult(blockSummaryResult);
	}


	/**
	 * 유저 입력 블록 검증 (기본-latest)
	 * @param blockNumberStr
	 * @return
	 * @throws CustomConnectorException
	 */
	private String getBlockNumberStr(String blockNumberStr) throws CustomConnectorException {
	    if (StaticValues.LATEST.equals(blockNumberStr)) {
	    	return blockNumberStr;
		}
		if (blockNumberStr.startsWith(StaticValues.START_HEX)) {
			return blockNumberStr;
		}

		if (isNotValidNumber(blockNumberStr)) {
			log.error("checkBlockReqParams isNotValidNumber.");
			throw new CustomConnectorException(CustomException.INVALID_PARAMS);
		}
	    return StaticValues.START_HEX + Long.toHexString(Long.valueOf(blockNumberStr));
	}

	private boolean isNotValidNumber(String param) {
		try {
			long result = Long.parseLong(param);
			return result < 0;
		} catch (Exception e) {
			return true;
		}
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
		List<BigDecimal> priceList = blockResult.getTransactions().stream()
				.map(each -> beautierUtils.getDecimalFromHex(each.getGasPrice()))
				.collect(Collectors.toList());
		Map<String, Integer> map = priceList.stream().collect(Collectors.groupingBy(each -> beautierUtils.getFormattedNumberStr(each),
				Collectors.reducing(0, e -> 1, Integer::sum)));
		Map<String, Integer> orderedMap = order.getOrderedMap(map);
		String max = beautierUtils.getFormattedNumberStr(priceList.stream().max(BigDecimal::compareTo).orElse(BigDecimal.ZERO));
		String min = beautierUtils.getFormattedNumberStr(priceList.stream().min(BigDecimal::compareTo).orElse(BigDecimal.ZERO));
		String avg = beautierUtils.getFormattedNumberStr(priceList.stream()
				.mapToDouble(each -> beautierUtils.getFormattedNumberDouble(each)).average().getAsDouble());
		long blockNumber = beautierUtils.getHexToLong(blockResult.getNumber());
		long txCnt = blockResult.getTransactions().size(); // tx cnt

		return BlockSummaryResult.builder()
			.orderedPriceTxCntMap(orderedMap)
			.averageGasPrice(avg).maxGasPrice(max).minGasPrice(min)
			.newestBlockNumber(blockNumber)
			.txBlockCount(txCnt)
			.build();
	}
}