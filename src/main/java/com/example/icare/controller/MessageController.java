package com.example.icare.controller;

import com.example.icare.domain.Nutritionist;
import com.example.icare.domain.Patient;
import com.example.icare.repository.NutritionistRepository;
import com.example.icare.repository.PatientRepository;
import com.example.icare.service.MessageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;
    private NutritionistRepository nutritionistRepository;
    private PatientRepository patientRepository;


    //send message from specific nutritionist to specific patient with or without attachment
    @PostMapping("/send/{nutritionist_id}/{patient_id}")
    public void sendMessage(@PathVariable("nutritionist_id") Long nutritionistId, @PathVariable("patient_id") Long patientId, @RequestParam String content, @RequestParam(required = false) MultipartFile attachment) throws IOException {
        Nutritionist nutritionist = nutritionistRepository.findById(nutritionistId).orElseThrow(() -> new EntityNotFoundException("Nutritionist not found"));
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        byte[] attachmentData = null;

        if (attachment != null && !attachment.isEmpty()) {
            attachmentData = attachment.getBytes();

        }

        messageService.sendMessage(nutritionist, patient, content, attachmentData);
    }

}
