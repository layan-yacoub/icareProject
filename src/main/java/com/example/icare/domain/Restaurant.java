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
    @Column(name="email")
    private String email;
    @Column(name="phone_number")
    private String phone_number;
    @Column(name="restaurant_name")
    private String restaurant_name ;
    @Column(name="restaurant_location")
    private String restaurant_location;
    @Column(name="restaurant_license")
    @Lob
    private byte[] restaurant_license  ;
    @Column(name="social_media")
    private String social_media  ;
    @Column(name="statues")
    private boolean status = true;

    @OneToOne
    @JoinColumn(name = "user_id")
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
