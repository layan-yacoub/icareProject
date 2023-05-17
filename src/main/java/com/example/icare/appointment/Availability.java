package com.example.icare.appointment;

import com.example.icare.domain.Nutritionist;
import com.example.icare.service.NutritionistService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "availability")
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nutritionist_id", nullable = false)
    private Nutritionist nutritionist;

    @Enumerated(EnumType.STRING)
    @Column(name = "available_date", nullable = false)
    private LocalDate availableDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    public Availability(LocalDate availableDate, LocalTime start, LocalTime end){
        setAvailableDate(availableDate);
        setStartTime(start);
        setEndTime(end);
    }

    @ManyToOne
    Appointment appointment;
}

