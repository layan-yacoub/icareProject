package com.example.icare.domain;

import com.example.icare.appointment.Appointment;

import com.example.icare.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Column(name = "n_first_name", nullable = false)
    private String nFirstName;
    @Column(name = "n_last_name", nullable = false)
    private String nLastName;
    @Column(name="email")
    private String email;
    @Column(name = "location", nullable = false)
    private String location;
    @Column(name = "center_name", nullable = false)
    private String centerName;
    @Column(name = "center_license", nullable = false)

    private String centerLicense;
    @Column(name = "nutritionist_license", nullable = false)

    private String nutritionistLicense;
    @Column(name = "experience")
    private int experience;
    @Column(name = "amount")
    private double amount = 20; // by default 20
    @Column(name="available_date")
    private LocalDate AvailableDate = LocalDate.of(2023,5,23); //by default
    public LocalTime startTime;
    private LocalTime endTime ;

    @Column(name = "link")
    private String link;
    @Column(name = "statues")
    private boolean status = true;
    @Column(name="rating")
    private int rating ;
    @Column(name="rating_counter")
    private int ratingCounter =0;
    @Column(name="total_rating")
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
    @CollectionTable(name = "off_days", joinColumns = @JoinColumn(name = "nutritionist_id"))
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "off_day")
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