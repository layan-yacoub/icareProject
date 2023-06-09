package com.example.icare.registrationRequest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NutritionistRequest {
    private String firstName;
    private String lastName;
    private String email  ;
    private String password ;
    private String location ;
    private String centerName;
    private String centerLicense ;
    private String nutritionistLicense;
    private int experience ;
}
