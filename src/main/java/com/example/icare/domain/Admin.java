package com.example.icare.domain;
import jakarta.persistence.*;
import lombok.*;

@ToString
@EqualsAndHashCode
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Admin {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long admin_id; //primary key
    private String email;
    private String password;
    private String name;



}
