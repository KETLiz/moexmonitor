// WebController.java
package com.example.moexmonitor.controllers;

import com.example.moexmonitor.models.SpreadInfo;
import com.example.moexmonitor.services.SpreadAnalysisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebController {
    private final SpreadAnalysisService spreadAnalysisService;

    public WebController(SpreadAnalysisService spreadAnalysisService) {
        this.spreadAnalysisService = spreadAnalysisService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<SpreadInfo> spreads = spreadAnalysisService.getAllSpreadData();
        model.addAttribute("spreads", spreads);
        return "index";
    }
}
