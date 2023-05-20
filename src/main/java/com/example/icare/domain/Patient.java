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
    @Column
    private Long patient_id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private LocalDate dob;
    @Column
    private String city;
    @Column
    private String gender;
    @Column
    private double weight;
    @Column
    private double height;
    @Column
    private String disease;
    @Column
    private String lifestyle;
    @Column
    @Lob
    private byte[] InBody;
    @Column
    @Lob
    private byte[] labMedicalReports;
    @Column
    @Lob
    private byte[] upload_pdf;
    @Column
    private boolean status =true;

    @OneToOne
    @JoinColumn
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






