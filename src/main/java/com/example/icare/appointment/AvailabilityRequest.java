package com.example.icare.appointment;

import com.example.icare.domain.Nutritionist;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AvailabilityRequest {

    private Nutritionist nutritionist;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
