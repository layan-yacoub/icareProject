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
    @Column( nullable = false)
    private String nFirstName;
    @Column( nullable = false)
    private String nLastName;
    @Column()
    private String email;
    @Column( nullable = false)
    private String location;
    @Column( nullable = false)
    private String centerName;
    @Column( nullable = false)

    private String centerLicense;
    @Column( nullable = false)

    private String nutritionistLicense;
    @Column()
    private int experience;
    @Column
    private double amount = 20; // by default 20
    @Column
    private LocalDate AvailableDate = LocalDate.of(2023,5,23); //by default
    public LocalTime startTime;
    private LocalTime endTime ;

    @Column
    private String link;
    @Column
    private boolean status = true;
    @Column
    private int rating ;
    @Column
    private int ratingCounter =0;
    @Column
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
    @CollectionTable( joinColumns = @JoinColumn)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column()
    private List<LocalDate> offDays = new ArrayList<>();



    //message list
    @OneToMany(mappedBy = "nutritionist", cascade = CascadeType.ALL)  // Relationships with Report entity
    private List<Message> messages = new ArrayList<>();

    @OneToOne
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