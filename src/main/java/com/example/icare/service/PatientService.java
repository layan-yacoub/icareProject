package com.example.icare.service;

import com.example.icare.domain.Nutritionist;
import com.example.icare.domain.Patient;
import com.example.icare.repository.NutritionistRepository;
import com.example.icare.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final NutritionistRepository nutritionistRepository;

    public int getPatientAge(Long patientId) {
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if (patient == null) {
            throw new IllegalArgumentException("Patient not found");
        }
        LocalDate dob = patient.getDob();
        if (dob == null) {
            throw new IllegalArgumentException("Patient date of birth not found");
        }
        return Period.between(dob, LocalDate.now()).getYears();
    }  // Age counter
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
    public Patient getPatientById(Long patient_id) {
        return patientRepository.findById(patient_id).orElse(null);
    }
    public void deleteById(Long patientId) {
        patientRepository.deleteById(patientId);
    }
    public void rateNutritionist(Long patientId, Long nutritionistId, int rating) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + patientId));

        Nutritionist nutritionist = nutritionistRepository.findById(nutritionistId)
                .orElseThrow(() -> new EntityNotFoundException("Nutritionist not found with ID: " + nutritionistId));

        nutritionist.setTotalRating(nutritionist.getTotalRating()+rating);
        nutritionist.setRatingCounter(nutritionist.getRatingCounter()+1);
        int nutritionistRate = nutritionist.getTotalRating()/nutritionist.getRatingCounter();
        nutritionist.setRating(nutritionistRate);

        patientRepository.save(patient);
        nutritionistRepository.save(nutritionist);
    }// RATE NUTRITIONIST

    public Optional<Patient> findById(Long patientId) {
       return patientRepository.findById(patientId);
    }
}
