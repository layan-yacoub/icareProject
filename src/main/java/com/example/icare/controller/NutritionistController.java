package com.example.icare.controller;

import com.example.icare.domain.Nutritionist;
import com.example.icare.domain.Patient;
import com.example.icare.registrationRequest.AmountRequest;
import com.example.icare.registrationRequest.ChangeEmailRequest;
import com.example.icare.registrationRequest.OffDay;
import com.example.icare.registrationRequest.SetStartEndTime;
import com.example.icare.repository.NutritionistRepository;
import com.example.icare.repository.PatientRepository;
import com.example.icare.service.NutritionistService;
import com.example.icare.service.PatientService;
import com.example.icare.user.InvalidPasswordException;
import com.example.icare.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController //is used in REST Web services & mark class as Controller Class
@RequestMapping(path ="api/nutritionist") //is used at the class level while
public class NutritionistController {
    private final NutritionistService nutritionistService;
    private final PatientService patientService;
    private final PatientRepository patientRepository;
    private final NutritionistRepository nutritionistRepository;


    @Autowired
    public NutritionistController(NutritionistService nutritionistService, PatientService patientService, PatientRepository patientRepository, NutritionistRepository nutritionistRepository) {
        this.nutritionistService = nutritionistService;
        this.patientService= patientService;
        this.patientRepository = patientRepository;
        this.nutritionistRepository = nutritionistRepository;
    }

    // APPOINTMENT SCHEDULE
   @PostMapping ("time")
    public ResponseEntity<?> setStartAndEndTime(  @RequestBody SetStartEndTime setStartEndTime){
       Optional<Nutritionist> optionalNutritionist = nutritionistService.findById(setStartEndTime.getNutritionist_id());
       if (optionalNutritionist.isEmpty()) {
           return ResponseEntity.notFound().build();
       }
       nutritionistService.setStartAndEndTime(setStartEndTime.getNutritionist_id(),setStartEndTime.getStart(),setStartEndTime.getEnd());
       return ResponseEntity.ok("save");
   }

    @PostMapping("/off-days")
    public ResponseEntity<?> addOffDay(@RequestBody OffDay offDay) throws ChangeSetPersister.NotFoundException {
        nutritionistService.addOffDay(offDay.getNutritionist_id(), offDay.getOffDay());
        return ResponseEntity.status(HttpStatus.CREATED).body("Off day added successfully.");
    }

    @DeleteMapping("/off-days")
    public ResponseEntity<String> removeOffDay(@RequestBody OffDay offDay) {
        nutritionistService.removeOffDay(offDay.getNutritionist_id(), offDay.getOffDay());
        return ResponseEntity.ok("Off day removed successfully.");
    }


    //SET AMOUNT
    @PutMapping ("/amountOfAppointment") // Set amount of appointments for a nutritionist
    public ResponseEntity<?> setAmountOfAppointments(@RequestBody AmountRequest amountRequest) throws ChangeSetPersister.NotFoundException {
        Optional<Nutritionist> optionalNutritionist = nutritionistService.findById(amountRequest.getNutritionist_id());
        if (optionalNutritionist.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Nutritionist nutritionist = optionalNutritionist.get();
        nutritionistService.setAmountOfAppointments(amountRequest.getNutritionist_id(), amountRequest.getAmount());
        nutritionistService.saveNutritionist(nutritionist);

        String message = "Amount of appointments set successfully: " +amountRequest.getAmount();
        return ResponseEntity.ok(message);
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

    @PutMapping("/email") //change email
    public ResponseEntity<String> changeEmail(@RequestBody ChangeEmailRequest changeEmailRequest) {
        try {
            nutritionistService.changeEmail(changeEmailRequest.getNutritionistId(), changeEmailRequest.getPassword(), changeEmailRequest.getNewEmail());
            return ResponseEntity.ok("Email changed successfully");
        } catch (InvalidPasswordException e) {
            return ResponseEntity.badRequest().body("Invalid password");
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }




}
