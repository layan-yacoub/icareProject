package com.example.icare.domain;

import com.example.icare.appointment.Appointment;
import com.example.icare.appointment.Availability;
import com.example.icare.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@Entity
@Table(name = "nutritionist")
public class Nutritionist {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long nutritionist_id;
    @Column(name = "n_first_name", nullable = false)
    private String nFirstName;
    @Column(name = "n_last_name", nullable = false)
    private String nLastName;
    @Column(name = "location", nullable = false)
    private String location;
    @Column(name = "center_name", nullable = false)
    private String centerName;
    @Column(name = "center_license", nullable = false)
    @Lob
    private byte[] centerLicense;
    @Column(name = "nutritionist_license", nullable = false)
    @Lob
    private byte[] nutritionistLicense;
    @Column(name = "experience")
    private int experience;
    @Column(name = "amount")
    private double amount = 20; // by default 20
    @Column(name="available_date")
    private LocalDate AvailableDate = LocalDate.of(2023,5,23); //by default
    @Column(name = "start_time")
    private LocalDateTime startDateTime = AvailableDate.atTime(9, 0); //  by default Starting time for appointments (e.g., 9 AM);
    @Column(name = "end_time")
    private LocalDateTime endDateTime = AvailableDate.atTime(17, 0); //  by default Ending time for appointments (e.g., 5 PM);
    @Column(name = "link")
    private String link;
    @Column(name = "statues")
    private boolean statues = true;
    @Column(name="rating")
    private int rating =0;
    @Column(name="rating_counter")
    private int ratingCounter =0;
    //Availability List
    @OneToMany(mappedBy = "nutritionist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Availability> availabilities = new ArrayList<>();


    //Appointments Lists
    @OneToMany(mappedBy = "nutritionist", cascade = CascadeType.ALL) // One-to-many relationship with appointments
    private List<Appointment> appointments = new ArrayList<>();
    @OneToMany(mappedBy = "nutritionist", cascade = CascadeType.ALL)
    private List<Appointment> bookedAppointments = new ArrayList<>();

    //message list
    @OneToMany(mappedBy = "nutritionist", cascade = CascadeType.ALL)  // Relationships with Report entity
    private List<Message> messages = new ArrayList<>();

   @Column(name = "user_id", insertable = false, updatable = false)
   @OneToOne(fetch = FetchType.LAZY)
   @PrimaryKeyJoinColumn
   private User user;
    public Nutritionist() { }

    public Nutritionist(String firstName,String lastName,String location, String centerName, byte[] centerLicense, byte[] nutritionistLicense, int experience) {
        this.nFirstName=firstName;
        this.nLastName=lastName;
        this.location = location;
        this.centerName = centerName;
        this.centerLicense = centerLicense;
        this.nutritionistLicense = nutritionistLicense;
        this.experience = experience;
    }

    public void addAppointments(Appointment appointment)
    { appointments.add(appointment);
    }
}