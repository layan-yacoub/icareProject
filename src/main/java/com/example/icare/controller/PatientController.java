package com.example.icare.controller;

import com.example.icare.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController //is used in REST Web services & mark class as Controller Class
@RequestMapping(path ="api/patient") //is used at the class level while
public class PatientController {
    private final PatientService patientService;
@Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
}
