package com.example.icare.controller;

import com.example.icare.appointment.Appointment;
import com.example.icare.appointment.AvailabilityRequest;
import com.example.icare.domain.Nutritionist;
import com.example.icare.domain.Patient;
import com.example.icare.repository.PatientRepository;
import com.example.icare.service.NutritionistService;
import com.example.icare.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.Month;
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
        this.patientService = patientService;
        this.patientRepository = patientRepository;
    }

    // APPOINTMENT SCHEDULE
    @PostMapping("/{nutritionist_id}/generateMonthlyAppointments") //generateMonthlyAppointments and exclude a list of days of the week
    public ResponseEntity<List<Appointment>> generateMonthlyAppointments(@PathVariable Long nutritionist_id , int year, Month month,
                                                                         LocalTime startTime, LocalTime endTime, List<DayOfWeek> excludedDays){

        Optional<Nutritionist> nutritionistOptional = nutritionistService.findById(nutritionist_id);

        if (nutritionistOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        nutritionistService.generateMonthlyAppointments(nutritionist_id,year,month,startTime,endTime,excludedDays);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("/{nutritionistId}/availability") //  add an available time slot for a specific date
    public ResponseEntity<String> addAvailability(
            @PathVariable("nutritionistId") Long nutritionistId,
            @RequestBody AvailabilityRequest availabilityRequest) {
        nutritionistService.addAvailability(nutritionistId, availabilityRequest);
        return ResponseEntity.ok("Availability added successfully");
    }
    @DeleteMapping("/{nutritionistId}/availability")// Endpoint to remove an available time slot for a specific date
    public ResponseEntity<String> removeAvailability(
            @PathVariable("nutritionistId") Long nutritionistId,
            @RequestBody AvailabilityRequest availabilityRequest) {
        nutritionistService.removeAvailability(nutritionistId, availabilityRequest);
        return ResponseEntity.ok("Availability removed successfully");
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




}
