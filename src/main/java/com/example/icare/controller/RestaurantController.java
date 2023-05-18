package com.example.icare.controller;

import com.example.icare.repository.RestaurantRepository;
import com.example.icare.service.RestaurantService;
import com.example.icare.user.InvalidPasswordException;
import com.example.icare.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController //is used in REST Web services & mark class as Controller Class
@RequestMapping(path ="api/restaurant") //is used at the class level while
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final RestaurantRepository restaurantRepository;
    @Autowired
    public RestaurantController(RestaurantService restaurantService, RestaurantRepository restaurantRepository) {
        this.restaurantService = restaurantService;
        this.restaurantRepository = restaurantRepository;
    }

    //UPDATE CONTACT INFO
    @PutMapping("/{restaurant_id}/phone_number")//update phone_number
    public ResponseEntity<String> setPhoneNumber (@PathVariable Long restaurant_id , String phone_number){
        try {
            restaurantService.setPhoneNumber(restaurant_id,phone_number);
            return ResponseEntity.ok("Email changed successfully");
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{restaurant_id}/social_media") //update social_media
    public ResponseEntity<String> setSocialMediaAccount (@PathVariable Long restaurant_id , String social_media){
        try {
            restaurantService.setSocialMediaAccount(restaurant_id,social_media);
            return ResponseEntity.ok("Email changed successfully");
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    //CHANGE EMAIL
    @PutMapping("/{restaurant_id}/email") //change email
    public ResponseEntity<String> changeEmail(@PathVariable("restaurant_id") Long restaurantId,
                                              @RequestParam String password,
                                              @RequestParam String newEmail) {
        try {
            restaurantService.changeEmail(restaurantId, password, newEmail);
            return ResponseEntity.ok("Email changed successfully");
        } catch (InvalidPasswordException e) {
            return ResponseEntity.badRequest().body("Invalid password");
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
