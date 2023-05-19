package com.example.icare.registrationRequest;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PatientRequest {
    private String firstName;
    private String lastName;
    private String email  ;
    private String password ;
    private String city ;
    private LocalDate dob;
    private String gender;
    private double height ;
    private double weight;
    private String lifestyle ;
    private String Disease ;
}
