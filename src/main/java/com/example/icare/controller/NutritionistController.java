package com.example.icare.controller;

import com.example.icare.service.NutritionistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //is used in REST Web services & mark class as Controller Class
@RequestMapping(path ="api/nutritionist") //is used at the class level while
public class NutritionistController {
    private final NutritionistService nutritionistService;
    @Autowired
    public NutritionistController(NutritionistService nutritionistService) {
        this.nutritionistService = nutritionistService;
    }



}
