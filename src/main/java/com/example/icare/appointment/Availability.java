package com.example.icare.appointment;

import com.example.icare.domain.Nutritionist;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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


    @Column(name = "start", nullable = false)
    private LocalDateTime startTimeDate;

    @Column(name = "end", nullable = false)
    private LocalDateTime endTimeDate;

    public Availability(LocalDateTime start, LocalDateTime end){

        setStartTimeDate(start);
        setEndTimeDate(end);
    }

    @ManyToOne
    Appointment appointment;
}

