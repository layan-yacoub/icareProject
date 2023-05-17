package com.example.icare.registrationRequest;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
@Data
@AllArgsConstructor
public class PatientRequest {
    private String firstName;
    private String lastName;
    private String city ;
    private LocalDate dob;
    private String gender;
    private double height ;
    private double weight;
    private String lifestyle ;
    private String Disease ;
}
