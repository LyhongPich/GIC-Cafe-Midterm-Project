package gic.i4b.group6.CafeManagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import gic.i4b.group6.CafeManagement.services.HistoryService;

@Controller
public class HistoryController {
    private HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/admin/history")
    public String historyView(Model model) {
        model.addAttribute("histories", historyService.getAllHistory());
        return "Admin/history";
    }
}
