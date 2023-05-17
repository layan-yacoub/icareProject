package com.example.icare.registrationRequest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NutritionistRequest {
    private String firstName;
    private String lastName;
    private String location ;
    private String centerName;
    private byte[] centerLicense ;
    private byte[] nutritionistLicense;
    private int experience ;
}
