package com.example.icare.service;

import com.example.icare.domain.Report;
import com.example.icare.repository.AdminRepository;
import com.example.icare.repository.ReportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final ReportRepository reportRepository;



}


