package com.example.icare.domain;
import com.example.icare.user.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long restaurant_id  ; // primary key derived from the superclass user_id and concatenated with a character 'R'
    private String  phone_number;
    private String restaurant_name ;
    private String restaurant_location;
    @Lob
    private byte[] restaurant_license  ;
    private String social_media  ;
    private boolean statues;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;



}
