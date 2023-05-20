package com.example.icare.domain;

import com.example.icare.appointment.Appointment;
import com.example.icare.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long patient_id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dob;
    private String city;
    private String gender;
    private double weight;
    private double height;
    private String disease;
    private String lifestyle;
    @Lob
    private byte[] InBody;
    @Lob
    private byte[] labMedicalReports;
    @Lob
    private byte[] upload_pdf;
    private boolean status =true;

    @OneToOne
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL,fetch = FetchType.LAZY) // One-to-many relationship with appointment
    private List<Appointment>  appointments = new ArrayList<>();

    @OneToMany (mappedBy = "patient", cascade = CascadeType.ALL)// One-to-many relationship with message
    private List<Message>  messages = new ArrayList<>();

    public Patient() {
    }

    public Patient(String email, String firstName, String lastName, LocalDate dob, String city, String gender, double weight, double height, String disease, String lifestyle) {
        this.email=email;
        this.firstName=firstName;
        this.lastName=lastName;
        this.dob = dob;
        this.city = city;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.disease = disease;
        this.lifestyle = lifestyle;
    }

}






