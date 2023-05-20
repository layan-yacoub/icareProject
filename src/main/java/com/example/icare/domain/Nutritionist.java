package com.example.icare.domain;

import com.example.icare.appointment.Appointment;

import com.example.icare.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@Entity
@Table
public class Nutritionist {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long nutritionist_id;
    private String nFirstName;
    private String nLastName;
    private String email;
    private String location;
    private String centerName;
    private String centerLicense;
    private String nutritionistLicense;
    private int experience;
    private double amount = 20; // by default 20
    private LocalDate AvailableDate = LocalDate.of(2023,5,23); //by default
    public LocalTime startTime;
    private LocalTime endTime ;

    private String link;
    private boolean status = true;
    private int rating ;
    private int ratingCounter =0;
    private int totalRating=0;


    //Appointments Lists
    @OneToMany(mappedBy = "nutritionist",fetch = FetchType.EAGER, cascade = CascadeType.ALL) // One-to-many relationship with appointments
    private List<Appointment> appointments = new ArrayList<>();

    //Booked Appointments ADD-REMOVE
    @OneToMany(mappedBy = "nutritionist", fetch = FetchType.EAGER)
    private List<Appointment> bookedAppointments = new ArrayList<>();
    public List<Appointment> getBookedAppointments() {
        return bookedAppointments;
    }

    //Off Day List-add-remove
    @ElementCollection
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private List<LocalDate> offDays = new ArrayList<>();


    //message list
    @OneToMany(mappedBy = "nutritionist", cascade = CascadeType.ALL)  // Relationships with Report entity
    private List<Message> messages = new ArrayList<>();



    @OneToOne
    @JoinColumn
    private User user;
    public Nutritionist() { }

    public Nutritionist(String firstName,String lastName,String location, String centerName, String centerLicense, String nutritionistLicense, int experience) {
        this.nFirstName=firstName;
        this.nLastName=lastName;
        this.location = location;
        this.centerName = centerName;
        this.centerLicense = centerLicense;
        this.nutritionistLicense = nutritionistLicense;
        this.experience = experience;
    }

    public Nutritionist(String nFirstName, String nLastName, String email, String location, String centerName, String centerLicense,String nutritionistLicense, int experience) {
        this.nFirstName = nFirstName;
        this.nLastName = nLastName;
        this.email = email;
        this.location = location;
        this.centerName = centerName;
        this.centerLicense = centerLicense;
        this.nutritionistLicense = nutritionistLicense;
        this.experience = experience;
    }
}