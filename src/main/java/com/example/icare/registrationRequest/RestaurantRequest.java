package com.example.icare.registrationRequest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestaurantRequest {

    private String phone_number ;
    private String restaurant_name ;
    private String restaurant_location ;
    private byte[] restaurant_license ;
    private String social_media;
}
