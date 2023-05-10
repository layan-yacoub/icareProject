package com.example.icare.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

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
    private Timestamp Appointment_date;//the appointment that the patient booked

    //private String  patient_id; //foreign key
    //private String nutritionist_id;// foreign key
    //private Timestamp available_appointments; // should be a list

    @ManyToOne(fetch = FetchType.LAZY) // Many-to-one relationship with nutritionist
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY) // Many-to-one relationship with nutritionist
    @JoinColumn(name = "nutritionist_id")
    private Nutritionist nutritionist;



}
