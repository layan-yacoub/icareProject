package com.example.icare.appointment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.icare.domain.Nutritionist;
import com.example.icare.repository.NutritionistRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@ControllerAdvice
@RestController
@RequestMapping(path = "api/appointments")
public class AppointmentController  {

    private final AppointmentService appointmentService;

    private final NutritionistRepository nutritionistRepository;

    private final AppointmentRepository appointmentRepository;
    @Autowired
    public AppointmentController(NutritionistRepository nutritionistRepository, AppointmentService appointmentService, AppointmentRepository appointmentRepository) {
        this.nutritionistRepository = nutritionistRepository;
        this.appointmentService = appointmentService;
        this.appointmentRepository = appointmentRepository;
    }

    @SneakyThrows
    @GetMapping("/available/{nutritionist_id}")  // to show the available appointments for a specific nutritionist in a specific date
    public ResponseEntity<List<Appointment>> getAvailableAppointments(@PathVariable Long nutritionist_id, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        Optional<Nutritionist> nutritionistOptional = nutritionistRepository.findById(nutritionist_id);

        if (nutritionistOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Nutritionist nutritionist = nutritionistOptional.get();
        List<Appointment> availableAppointments = appointmentService.getAvailableAppointments(nutritionist, date);
        return ResponseEntity.ok(availableAppointments);
    }

    @PostMapping("/book/{nutritionist_id}")//to book an appointment with a specific nutritionist and pay
    public  ResponseEntity<Void> bookAppointment(@PathVariable Long nutritionist_id, @RequestParam("startDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime) {

        Optional<Nutritionist> nutritionistOptional = nutritionistRepository.findById(nutritionist_id);

        if (nutritionistOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Nutritionist nutritionist = nutritionistOptional.get();

        boolean isAppointmentBooked = appointmentService.bookAppointment(nutritionist, startTime);

        if (!isAppointmentBooked) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/nutritionists/{nutritionist_id}/appointments") //to see the appointments for specific nutritionist
    public ResponseEntity<List<Appointment>> getAppointmentsByNutritionistId(@PathVariable Long nutritionist_id) {
        Optional<Nutritionist> nutritionistOptional = nutritionistRepository.findById(nutritionist_id);

        if (nutritionistOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Nutritionist nutritionist = nutritionistOptional.get();
        List<Appointment> appointments = appointmentService.findByNutritionist(nutritionist);

        return ResponseEntity.ok(appointments);
    }

    @DeleteMapping("/{appointment_id}")//Delete the appointment from the database
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long appointment_id, @RequestParam Long nutritionistId) {
        Optional<Nutritionist> nutritionistOptional = nutritionistRepository.findById(nutritionistId);

        if (nutritionistOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Nutritionist nutritionist = nutritionistOptional.get();
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointment_id);

        if (appointmentOptional.isEmpty() || !appointmentOptional.get().getNutritionist().equals(nutritionist)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        appointmentService.deleteAppointmentById(appointment_id);
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

