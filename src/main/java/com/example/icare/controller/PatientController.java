package com.example.icare.controller;

import com.example.icare.domain.Nutritionist;
import com.example.icare.domain.Patient;
import com.example.icare.domain.Restaurant;
import com.example.icare.repository.PatientRepository;
import com.example.icare.service.NutritionistService;
import com.example.icare.service.PatientService;
import com.example.icare.service.RestaurantService;
import com.example.icare.user.InvalidPasswordException;
import com.example.icare.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController //is used in REST Web services & mark class as Controller Class
@RequestMapping(path ="api/patient") //is used at the class level while
public class PatientController {
    private final PatientService patientService;
    private final PatientRepository patientRepository;
    private final NutritionistService nutritionistService;
    private final RestaurantService restaurantService;

@Autowired
    public PatientController(PatientService patientService, PatientRepository patientRepository, NutritionistService nutritionistService, RestaurantService restaurantService) {
        this.patientService = patientService;
    this.patientRepository = patientRepository;
    this.nutritionistService = nutritionistService;
    this.restaurantService = restaurantService;
}

    //COUNT
    @GetMapping("/{patient_id}/age") // Age counter
    public ResponseEntity<Integer> getPatientAge(@PathVariable ("patient_id") Long patientId) {
        try {
            int age = patientService.getPatientAge(patientId);
            return ResponseEntity.ok(age);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();// Return HTTP 400 Bad Request
        }
    }

    //UPLOAD FILES
    @PostMapping("/{patient_id}/inbody")  //upload in-body file
    public ResponseEntity<?> uploadInbody(@PathVariable("patient_id") Long patientId,
                                          @RequestParam("file") MultipartFile file) {
        try {
            Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new RuntimeException("Patient not found"));

            byte[] InBody = file.getBytes();
            patient.setInBody(InBody);
            patientRepository.save(patient);

            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/{patient_id}/upload_pdf")   //upload pdf file
    public ResponseEntity<?> setUpload_pdf(@PathVariable("patient_id") Long patientId,
                                           @RequestParam("file") MultipartFile file) {
        try {
            Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new RuntimeException("Patient not found"));

            byte[] upload_pdf = file.getBytes();
            patient.setUpload_pdf(upload_pdf);
            patientRepository.save(patient);

            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/{patient_id}/lab_medical_reports")   //upload lab_medical_reports file
    public ResponseEntity<?> setLabMedicalReports(@PathVariable("patient_id") Long patientId,
                                           @RequestParam("file") MultipartFile file) {
        try {
            Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new RuntimeException("Patient not found"));

            byte[] lab_medical_reports = file.getBytes();
            patient.setLabMedicalReports(lab_medical_reports);
            patientRepository.save(patient);

            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //DELETE FILES
    @DeleteMapping("/{patient_id}/inbody") //delete in-body file
    public ResponseEntity<?> deleteInbody(@PathVariable("patient_id") Long patientId) {
        try {
            Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new RuntimeException("Patient not found"));

            patient.setInBody(null);
            patientRepository.save(patient);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/{patient_id}/upload_pdf") // delete pdf file
    public ResponseEntity<?> deleteUploadPdf(@PathVariable("patient_id") Long patientId) {
        try {
            Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new RuntimeException("Patient not found"));

            patient.setUpload_pdf(null);
            patientRepository.save(patient);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/{patient_id}/lab_medical_reports") //lab_medical_reports file
    public ResponseEntity<?> deleteLabMedicalReports(@PathVariable("patient_id") Long patientId) {
        try {
            Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new RuntimeException("Patient not found"));

            patient.setLabMedicalReports(null);
            patientRepository.save(patient);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    //VIEW
    @GetMapping("/centers")//list of the centers (Nutritionist)
    public ResponseEntity<List<Nutritionist>> getAllCenters() {
        List<Nutritionist> centers = nutritionistService.getAllCenters();
        return new ResponseEntity<>(centers, HttpStatus.OK);
    }
    @GetMapping("/restaurants")//list of the restaurants
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }


    //RATE-NUTRITIONIST
    @PostMapping("/{patient_id}/nutritionists/{nutritionist_id}/rate") //the patient rate the nutritionist
    public ResponseEntity<String> rateNutritionist(
            @PathVariable("patient_id") Long patientId,
            @PathVariable("nutritionist_id") Long nutritionistId,
            @RequestParam("rating") int rating
    ) {
        patientService.rateNutritionist(patientId, nutritionistId, rating);
        return ResponseEntity.ok("Rating saved successfully.");
    }

    //CHANGE EMAIL

    @PutMapping("/{patient_id}/email") //change email
    public ResponseEntity<String> changeEmail(@PathVariable("patient_id") Long patientId,
                                              @RequestParam String password,
                                              @RequestParam String newEmail) {
        try {
            patientService.changeEmail(patientId, password, newEmail);
            return ResponseEntity.ok("Email changed successfully");
        } catch (InvalidPasswordException e) {
            return ResponseEntity.badRequest().body("Invalid password");
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
