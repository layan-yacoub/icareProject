package com.example.icare.domain;

import com.example.icare.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
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
    private Long patient_id;     // derived from the superclass user_id and concatenated with a character 'P'
    private String p_first_name;
    private String p_last_name;
    private Date dob;
    private String city;
    private String gender;
    private int weight;
    private int height;
    private String disease;
    private String lifestyle;
    @Lob
    private byte[] InBody;
    private String bmi;
    @Lob
    private byte[] lab_medical_reports;
    @Lob
    private byte[] upload_pdf;
    private boolean statues;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL) // One-to-many relationship with appointment
    private List<Appointment>  appointments = new ArrayList<>();
    @OneToMany (mappedBy = "patient", cascade = CascadeType.ALL)// One-to-many relationship with message
    private List<Message>  messages = new ArrayList<>();

    public Patient() {
    }



}






