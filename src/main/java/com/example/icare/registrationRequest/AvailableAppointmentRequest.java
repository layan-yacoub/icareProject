package com.example.icare.registrationRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@NonNull
public class AvailableAppointmentRequest {
   private Long nutritionist_id;
    private LocalDate date;
}
