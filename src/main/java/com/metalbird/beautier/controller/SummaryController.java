package com.metalbird.beautier.controller;

import com.metalbird.beautier.controller.model.SummaryResult;
import com.metalbird.beautier.service.SummaryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SummaryController {

    @Autowired
    private SummaryService summaryService;

    @GetMapping("/gasPrice")
    public SummaryResult gasPrice() {
        return summaryService.getGasSummaryResult();
    }
}