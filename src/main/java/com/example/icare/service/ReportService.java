package com.example.icare.service;

import com.example.icare.domain.Report;
import com.example.icare.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
@Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    //save report
    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }

// view, delete, and resolve report

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public List<Report> getAllVisibleReports() {
        return reportRepository.findByVisible(true);
    }
    public Report getReportById(Long reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(() -> new ReportNotFoundException("Report not found with ID: " + reportId));
    }

    public void deleteReport(Long reportId) {
        if (reportRepository.existsById(reportId)) {
            reportRepository.deleteById(reportId);
        } else {
            throw new ReportNotFoundException("Report not found with ID: " + reportId);
        }
    }


    public void resolveReport(Long reportId) {

        Optional<Report> optionalReport = reportRepository.findById((long) reportId);
        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            report.setVisible(false);
            reportRepository.save(report);
        } else {
            // Handle case where report with the given ID is not found
            throw new IllegalArgumentException("Report not found with ID: " + reportId);
        }
    }

}




