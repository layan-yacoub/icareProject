package com.example.icare.controller;

import com.example.icare.appointment.Appointment;

import com.example.icare.domain.Nutritionist;
import com.example.icare.domain.Patient;
import com.example.icare.registrationRequest.GenerateMonthlyAppointments;
import com.example.icare.repository.PatientRepository;
import com.example.icare.service.NutritionistService;
import com.example.icare.service.PatientService;
import com.example.icare.user.InvalidPasswordException;
import com.example.icare.user.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController //is used in REST Web services & mark class as Controller Class
@RequestMapping(path ="api/nutritionist") //is used at the class level while
public class NutritionistController {
    private final NutritionistService nutritionistService;
    private final PatientService patientService;
    private final PatientRepository patientRepository;


    @Autowired
    public NutritionistController(NutritionistService nutritionistService, PatientService patientService, PatientRepository patientRepository) {
        this.nutritionistService = nutritionistService;
        this.patientService= patientService;
        this.patientRepository = patientRepository;
    }

    // APPOINTMENT SCHEDULE
    @PostMapping("/generateMonthlyAppointments") ///generateMonthlyAppointments and exclude a list of days of the week
    public ResponseEntity<List<Appointment>> generateMonthlyAppointments(@RequestParam ("nutritionist_id")Long nutritionist_id,@RequestBody GenerateMonthlyAppointments generateMonthlyAppointments) {

        System.out.println(generateMonthlyAppointments);
        Optional<Nutritionist> nutritionistOptional = nutritionistService.findById(nutritionist_id);

        if (nutritionistOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        try {
            List<Appointment> generatedAppointments = nutritionistService.generateMonthlyAppointments(nutritionist_id, generateMonthlyAppointments.getYear(),
                    generateMonthlyAppointments.getMonth(), generateMonthlyAppointments.getStartTime(),generateMonthlyAppointments.getEndTime() ,generateMonthlyAppointments.getExcludedDays());

            return ResponseEntity.status(HttpStatus.CREATED).body(generatedAppointments);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @GetMapping("/{nutritionist_id}/generatedAppointments") //get ALL the generated appointments for a specific nutritionist
    public ResponseEntity<List<Appointment>> getGeneratedAppointments(@PathVariable Long nutritionist_id) {
        try {
            List<Appointment> generatedAppointments = nutritionistService.getGeneratedAppointments(nutritionist_id);
            return ResponseEntity.ok(generatedAppointments);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{nutritionist_id}/generatedAppointments/{year}/{month}")//get the generated appointments in specific month for a specific nutritionist
    public ResponseEntity<List<Appointment>> getGeneratedAppointments(
            @PathVariable("nutritionist_id") Long nutritionistId,
            @PathVariable("year") int year,
            @PathVariable("month") Month month) {
        try {
            YearMonth yearMonth = YearMonth.of(year, month);
            List<Appointment> generatedAppointments = nutritionistService.getGeneratedAppointments(nutritionistId, yearMonth);
            return ResponseEntity.ok(generatedAppointments);

        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //SET AMOUNT
    @PutMapping("/{nutritionist_id}/amountOfAppointment") // Set amount of appointments for a nutritionist
    public ResponseEntity<String> setAmountOfAppointments(@PathVariable("nutritionist_id") Long nutritionistId,
                                                          @RequestParam double amount) throws ChangeSetPersister.NotFoundException {
        Optional<Nutritionist> optionalNutritionist = nutritionistService.findById(nutritionistId);
        if (optionalNutritionist.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Nutritionist nutritionist = optionalNutritionist.get();
        nutritionistService.setAmountOfAppointments(nutritionistId,amount);
        nutritionistService.saveNutritionist(nutritionist);

        String message = "Amount of appointments set successfully: " + amount;
        return ResponseEntity.ok(message);
    }

   //GET
    @GetMapping("/{nutritionist_id}/rate") //get the rating for a nutritionist
    public ResponseEntity<Integer>getRateOfNutritionist(@PathVariable Long nutritionist_id){
        Optional<Nutritionist> nutritionist = nutritionistService.findById(nutritionist_id);

        if (nutritionist.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        int rate = nutritionistService.getRating(nutritionist_id)  ;
        return ResponseEntity.ok(rate);
    }

    @GetMapping("/{patient_id}/inbody")//get in-body for a specific patient
    public ResponseEntity<byte[]> getInbody(@PathVariable("patient_id") Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        byte[] InBody = patient.getInBody();

        return ResponseEntity.ok().body(InBody);
    }

    @GetMapping("/{patient_id}/upload_pdf") //get a pdf from a patient
    public ResponseEntity<byte[]> getUpload_pdf(@PathVariable("patient_id") Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        byte[] upload_pdf = patient.getUpload_pdf();

        return ResponseEntity.ok().body(upload_pdf);
    }

    @GetMapping("/{patient_id}") // view patient's record
    public ResponseEntity<Map<String, Object>> viewPatientRecord(@PathVariable Long patient_id) {
        Patient patient = patientService.getPatientById(patient_id);

        Map<String, Object> response = new HashMap<>();
        response.put("firstName", patient.getFirstName());
        response.put("lastName", patient.getLastName());
        response.put("weight", patient.getWeight());
        response.put("height", patient.getHeight());
        response.put("diseases", patient.getDisease());
        response.put("medical report", patient.getUpload_pdf());
        response.put("Inbody", patient.getInBody());
        response.put("age", patient.getDob());
        response.put("lifestyle", patient.getLifestyle());

        return ResponseEntity.ok(response);
    }


    //CHANGE EMAIL

    @PutMapping("/{nutritionist_id}/email") //change email
    public ResponseEntity<String> changeEmail(@PathVariable("nutritionist_id") Long nutritionistId,
                                              @RequestParam String password,
                                              @RequestParam String newEmail) {
        try {
            nutritionistService.changeEmail(nutritionistId, password, newEmail);
            return ResponseEntity.ok("Email changed successfully");
        } catch (InvalidPasswordException e) {
            return ResponseEntity.badRequest().body("Invalid password");
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }




}
