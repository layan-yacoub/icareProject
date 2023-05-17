package com.example.icare.user;
import com.example.icare.domain.Report;
//import com.example.icare.token.Token;
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
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)    // Relationships with Report entity
    private List<Report> reports = new ArrayList<>();


    /*@OneToMany(mappedBy = "user")
    private List<Token> tokens;*/

    public User(String firstName, String lastName, String email, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role= Role.valueOf(role);

    }


    public User (Long user_id, String email, String password){
        this.email = email;
        this.password = password;
    }



}
