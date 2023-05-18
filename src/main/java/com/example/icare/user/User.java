package com.example.icare.user;
import com.example.icare.domain.Report;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id", nullable = false, unique = true)
    private Long user_id; //primary key
    @Column(name = "password", nullable = false)
    private String password;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)    // Relationships with Report entity
    private List<Report> reports = new ArrayList<>();


    public User (String password){
        this.password = password;
    }



}
