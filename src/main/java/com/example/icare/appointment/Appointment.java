package com.example.icare.appointment;

import com.example.icare.domain.Nutritionist;
import com.example.icare.domain.Patient;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="appointment")
public class Appointment  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long appointment_id ;

    //the appointment that the patient booked
    private LocalDateTime booked_date = LocalDateTime.now();

    //the time of the appointment
    @Column(nullable = false)
    private LocalDateTime startTime;
    @Column(nullable = false)
    private LocalDateTime endTime;

    // Many-to-one relationship with patient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    // Many-to-one relationship with nutritionist
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nutritionist_id")
    private Nutritionist nutritionist;

    @OneToMany(mappedBy = "Appointment", cascade = CascadeType.ALL) // One-to-many relationship with appointments
    private List<Availability> availableAppointments = new ArrayList<>();
    // get data from nutritionist
    @Column(name="amount")
    private double amount= nutritionist.getAmount();
    @Column(name="appointment_link")
    private String AppointmentLink =nutritionist.getLink();





}
