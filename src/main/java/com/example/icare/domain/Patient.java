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
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="patient_id")
    private Long patient_id;
    @Column(name="p_first_name")
    private String firstName;
    @Column(name="p_last_name")
    private String lastName;
    @Column(name="email")
    private String email;
    @Column(name="dob")
    private LocalDate dob;
    @Column(name="city")
    private String city;
    @Column(name="gender")
    private String gender;
    @Column(name="weight")
    private double weight;
    @Column(name="height")
    private double height;
    @Column(name="disease")
    private String disease;
    @Column(name="lifestyle")
    private String lifestyle;
    @Column(name="inbody")
    @Lob
    private byte[] InBody;
    @Column(name="lab_medical_reports")
    @Lob
    private byte[] labMedicalReports;
    @Column(name="upload_pdf")
    @Lob
    private byte[] upload_pdf;
    @Column(name="statues")
    private boolean status =true;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL) // One-to-many relationship with appointment
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






