package com.example.icare.service;

import com.example.icare.domain.Nutritionist;
import com.example.icare.domain.Restaurant;
import com.example.icare.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

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
}
