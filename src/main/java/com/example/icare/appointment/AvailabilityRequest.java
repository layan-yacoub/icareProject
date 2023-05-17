package com.example.icare.appointment;

import com.example.icare.domain.Nutritionist;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
public class AvailabilityRequest {

    private Nutritionist nutritionist;
    private LocalDate availableDate;
    private LocalTime startTime;
    private LocalTime endTime;
}
