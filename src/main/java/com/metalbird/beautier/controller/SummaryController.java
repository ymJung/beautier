package com.metalbird.beautier.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.metalbird.beautier.connector.model.CustomConnectorException;
import com.metalbird.beautier.controller.model.BeautierOrder;
import com.metalbird.beautier.controller.model.SummaryResult;
import com.metalbird.beautier.service.SummaryService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@Slf4j
public class SummaryController {

    @Autowired
    private SummaryService summaryService;

    @GetMapping("/gasPrice")
    public SummaryResult gasPrice(@RequestParam(required = false, defaultValue = "DESC") String order,
                                  @RequestParam(required = false, defaultValue = "latest") String blockNumberStr) {
        try {
            return summaryService.getGasSummaryResult(BeautierOrder.valueOf(order), blockNumberStr);
        } catch (CustomConnectorException e) {
            return new SummaryResult(false, e.getMessage());
        } catch (IllegalArgumentException e) {
            return new SummaryResult(false, "invalid order arg. choose one. order =" +
                    Arrays.asList(BeautierOrder.values()) + " blockNumberStr=[positive decimal or hex str(0xFF)]");
        } catch (Exception e) {
            log.error("unexpected exception. cause : ", e);
            return new SummaryResult(false, "server exception.");
        }
    }

    @GetMapping("/gasPrice/pretty")
    @ResponseBody
    public String gasPricePretty(@RequestParam(required = false, defaultValue = "DESC") String order,
                                  @RequestParam(required = false, defaultValue = "latest") String blockNumberStr) {
        SummaryResult summaryResult = gasPrice(order, blockNumberStr);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(summaryResult);
    }
}