package com.example.icare.controller;

import com.example.icare.domain.Report;
import com.example.icare.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="api/report")
public class ReportController {
    private final ReportService reportService;
@Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }
    // save report
    @PostMapping("/save")
    public ResponseEntity<Report> saveReport(@RequestBody Report report) {
        Report savedReport = reportService.saveReport(report);
        return ResponseEntity.ok(savedReport);
    }

}
