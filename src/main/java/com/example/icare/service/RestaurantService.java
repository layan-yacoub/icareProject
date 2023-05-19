package com.example.icare.service;

import com.example.icare.domain.Restaurant;
import com.example.icare.repository.RestaurantRepository;
import com.example.icare.user.InvalidPasswordException;
import com.example.icare.user.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public void deleteRestaurantById(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }

    public List<Restaurant> getAllRestaurants() {
       return restaurantRepository.findAll();
    }

    public void saveRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAllRestaurantsWithStatus(boolean status) {
        return restaurantRepository.findByStatus(status);

    }

    public Restaurant getRestaurantByEmail(String email) {
        return restaurantRepository.findByEmail(email);
    }

    public void changeEmail(Long restaurantId, String password, String newEmail) {

            Restaurant restaurant = restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            if (!(Objects.equals(password, restaurant.getUser().getPassword()))){
                throw new InvalidPasswordException("Invalid password");
            }

            restaurant.setEmail(newEmail);
            restaurantRepository.save(restaurant);

        }

    public void setPhoneNumber(Long restaurantId, String phoneNumber) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        restaurantRepository.getReferenceById(restaurantId).setPhone_number(phoneNumber);
        restaurantRepository.save(restaurant);
    }

    public void setSocialMediaAccount(Long restaurantId, String social_media) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        restaurantRepository.getReferenceById(restaurantId).setSocial_media(social_media);
        restaurantRepository.save(restaurant);
    }
}
