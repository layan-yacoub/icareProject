package com.example.icare.registrationRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class BookAppointmentRequest {
    private Long patient_id;
    private Long nutritionist_id;
   private LocalDateTime startTime;
}
