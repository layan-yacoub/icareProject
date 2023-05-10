package com.example.icare.service;

import com.example.icare.domain.Nutritionist;
import com.example.icare.repository.NutritionistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NutritionistService {

    private final NutritionistRepository nutritionistRepository;
    @Autowired
    public NutritionistService(NutritionistRepository nutritionistRepository) {
        this.nutritionistRepository = nutritionistRepository;
    }

    public List<Nutritionist> getAllNutritionists() {
        return nutritionistRepository.findAll();
    }

    public Nutritionist getNutritionistById(Long nutritionist_id) {
        return nutritionistRepository.findById(nutritionist_id).orElse(null);
    }

    public Nutritionist addNutritionist(Nutritionist nutritionist) {
        return nutritionistRepository.save(nutritionist);
    }

    public void deleteNutritionistById(Long nutritionist_id) {
        nutritionistRepository.deleteById(nutritionist_id);
    }
}
