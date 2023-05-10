package com.example.icare.domain;

import com.example.icare.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "nutritionist")
public class Nutritionist {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long nutritionist_id;
    private int user_id;
    private String n_first_name;
    private String n_last_name;
    private String location;
    private String center_name;
    @Lob
    private byte[] center_license;
    @Lob
    private byte[] nutritionist_license;
    private int experience;
    private double amount;
    @Column(nullable = false)
    private Timestamp startTime;
    @Column(nullable = false)
    private Timestamp endTime;
    private Timestamp availablitiy;
    private boolean statues;
    //@Column(name = "user_id", insertable = false, updatable = false)
    //private Long userId;

    @OneToMany(mappedBy = "nutritionist", cascade = CascadeType.ALL) // One-to-many relationship with appointments
    private List<Appointment> appointments = new ArrayList<>();
    @OneToMany(mappedBy = "nutritionist", cascade = CascadeType.ALL)  // Relationships with Report entity
    private List<Message> messages = new ArrayList<>();

   // @Column(name = "user_id", insertable = false, updatable = false)
   @OneToOne(fetch = FetchType.LAZY)
   @PrimaryKeyJoinColumn
   private User user;



    /*@PostPersist  //ensures that this method is called after the entity is persisted to the database.
    public void generateNutritionistId() {
        nutritionist_id = "N"+ getUser_id() ;
    }*/

   /* @PrePersist
    public void generateId() {
        this.nutritionist_id = "R" + user.getUser_id();
    }*/

    public Nutritionist() { }



}