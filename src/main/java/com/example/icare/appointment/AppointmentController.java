package com.example.icare.appointment;
import com.example.icare.domain.Patient;
import com.example.icare.registrationRequest.AvailableAppointmentRequest;
import com.example.icare.registrationRequest.BookAppointmentRequest;
import com.example.icare.registrationRequest.PatientNutritionistIdRequest;
import com.example.icare.repository.PatientRepository;
import com.example.icare.service.NutritionistService;
import com.example.icare.service.PatientService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.icare.domain.Nutritionist;
import com.example.icare.repository.NutritionistRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;





@RestController
@RequestMapping(path = "api/appointments")
public class AppointmentController  {

    private final AppointmentService appointmentService;

    private final NutritionistRepository nutritionistRepository;
    private final NutritionistService nutritionistService;

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    private final PatientService patientService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, NutritionistRepository nutritionistRepository,
                                 NutritionistService nutritionistService, AppointmentRepository appointmentRepository, PatientRepository patientRepository, PatientService patientService) {
        this.appointmentService = appointmentService;
        this.nutritionistRepository = nutritionistRepository;
        this.nutritionistService = nutritionistService;
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.patientService = patientService;
    }


    @SneakyThrows
    @GetMapping("/available")  // to show the available appointments for a specific nutritionist in a specific date
    public ResponseEntity<List<Appointment>> getAvailableAppointments(@RequestBody AvailableAppointmentRequest availableAppointmentRequest) {

        List<Appointment> availableAppointments = new ArrayList<>();
              availableAppointments=  appointmentService.getAvailableAppointments(availableAppointmentRequest.getNutritionist_id(), availableAppointmentRequest.getDate());

        if(availableAppointments==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(availableAppointments);
    }

    @PostMapping("/book")//to book an appointment with a specific nutritionist and pay
    public  ResponseEntity<?> bookAppointment(@RequestBody BookAppointmentRequest bookAppointmentRequest) {

        Optional<Nutritionist> nutritionistOptional = nutritionistRepository.findById(bookAppointmentRequest.getNutritionist_id());

        if (nutritionistOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Nutritionist nutritionist = nutritionistOptional.get();

        Optional<Patient> patientOptional = patientService.findById(bookAppointmentRequest.getPatient_id());

        if (patientOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Patient patient = patientOptional.get();

        boolean isAppointmentBooked = appointmentService.bookAppointment(patient, nutritionist, bookAppointmentRequest.getStartTime());

        if (!isAppointmentBooked) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

  // get the appointments for a specific user
    @GetMapping("/patients")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatientId(@RequestBody PatientNutritionistIdRequest patientIdRequest) {
        Patient patient= patientService.getPatientById(patientIdRequest.getPatient_id());
        List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patient);
        return ResponseEntity.ok(appointments);
    }

    @DeleteMapping("/{appointment_id}")//Delete the appointment from the database
    public ResponseEntity<Void> deleteAppointment(@PathVariable ("appointment_id")Long appointment_id) {
            Objects.requireNonNull(appointmentService).deleteAppointmentById(appointment_id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/{appointment_id}/link")//get link for a specific appointment
    public ResponseEntity<String> getAppointmentLink(@PathVariable Long appointment_id) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointment_id);

        if (appointmentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Appointment appointment = appointmentOptional.get();
        String appointmentLink = appointment.getAppointmentLink();

        if (appointmentLink == null || appointmentLink.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(appointmentLink);
    }


}

