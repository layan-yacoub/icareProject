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
@Table
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long restaurant_id  ; // primary key derived from the superclass user_id and concatenated with a character 'R'

    private String email;

    private String phone_number;

    private String restaurant_name ;

    private String restaurant_location;

    @Lob
    private byte[] restaurant_license  ;

    private String social_media  ;

    private boolean status = true;

    @OneToOne
    @JoinColumn
    private User user;

    public Restaurant(String email,String phone_number, String restaurant_name, String restaurant_location, byte[] restaurant_license, String social_media) {
        this.email=email;
        this.phone_number = phone_number;
        this.restaurant_name = restaurant_name;
        this.restaurant_location = restaurant_location;
        this.restaurant_license = restaurant_license;
        this.social_media = social_media;
    }
}
