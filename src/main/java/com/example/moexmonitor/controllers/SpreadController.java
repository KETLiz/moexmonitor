// SpreadController.java
package com.example.moexmonitor.controllers;

import com.example.moexmonitor.models.FuturesInstrument;
import com.example.moexmonitor.models.SpotInstrument;
import com.example.moexmonitor.models.SpreadInfo;
import com.example.moexmonitor.services.SpreadAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SpreadController {
    private final SpreadAnalysisService spreadAnalysisService;

    @Autowired
    public SpreadController(SpreadAnalysisService spreadAnalysisService) {
        this.spreadAnalysisService = spreadAnalysisService;
    }

    @GetMapping("spots")
    public List<SpotInstrument> getSpots() {
        return spreadAnalysisService.getSpots();
    }

    @GetMapping("futures")
    public List<FuturesInstrument> getFutures() {
        return spreadAnalysisService.getFutures();
    }

    @GetMapping("spreads")
    public Map<String, SpreadInfo> getAllSpreads() {
        return spreadAnalysisService.updateSpreadData();
    }
}