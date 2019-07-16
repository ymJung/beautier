package com.metalbird.beautier.controller;

import com.metalbird.beautier.controller.model.CustomNetworkTimeoutException;
import com.metalbird.beautier.controller.model.CustomNetworkUnreachableException;
import com.metalbird.beautier.controller.model.CustomUnexpectedException;
import com.metalbird.beautier.controller.model.SummaryResult;
import com.metalbird.beautier.service.SummaryService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SummaryController {

    @Autowired
    private SummaryService summaryService;

    @GetMapping("/gasPrice")
    public SummaryResult gasPrice() {
        try {
            return summaryService.getGasSummaryResult();
        } catch (CustomNetworkUnreachableException| CustomNetworkTimeoutException| CustomUnexpectedException e) {
            return new SummaryResult(false, e.getMessage());
        } catch (Exception e) {
            log.error("unexpected exception. cause : ", e);
            return new SummaryResult(false, "server exception.");
        }
    }
}