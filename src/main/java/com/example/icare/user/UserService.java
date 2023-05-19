package com.example.icare.user;
import com.example.icare.domain.Nutritionist;
import com.example.icare.domain.Patient;
import com.example.icare.domain.Restaurant;
import com.example.icare.registrationRequest.*;
import com.example.icare.repository.NutritionistRepository;
import com.example.icare.repository.PatientRepository;
import com.example.icare.repository.RestaurantRepository;
import com.example.icare.service.NutritionistService;
import com.example.icare.service.PatientService;
import com.example.icare.service.RestaurantService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
@AllArgsConstructor
public class UserService   {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final NutritionistRepository nutritionistRepository;
    private final RestaurantRepository restaurantRepository;
    private final PatientService patientService;
    private final NutritionistService nutritionistService;
    private final RestaurantService restaurantService;

    public Patient signupPatient(PatientRequest patientRequest) {
        User user = new User((patientRequest.getPassword()));
        Patient patient=new Patient(patientRequest.getEmail(), patientRequest.getFirstName(), patientRequest.getLastName(),patientRequest.getDob(), patientRequest.getCity(), patientRequest.getGender(),
                patientRequest.getWeight(), patientRequest.getHeight(), patientRequest.getDisease(), patientRequest.getLifestyle());

        patient.setStatus(true);
        userRepository.save(user);
        patientRepository.save(patient);

        return patient;
    }

    public Nutritionist signupNutritionist( NutritionistRequest nutritionistRequest) {
        User user = new User((nutritionistRequest.getPassword()));

        Nutritionist nutritionist = new Nutritionist(nutritionistRequest.getFirstName(),nutritionistRequest.getLastName(),nutritionistRequest.getEmail(),nutritionistRequest.getLocation(),nutritionistRequest.getCenterName(),
                nutritionistRequest.getCenterLicense(),nutritionistRequest.getNutritionistLicense(),nutritionistRequest.getExperience());

        nutritionist.setStatus(false);
        userRepository.save(user);
        nutritionistRepository.save(nutritionist);
        return (nutritionist);
    }
    @Transactional
    public Restaurant signupRestaurant( RestaurantRequest restaurantRequest) {
        User user = new User((restaurantRequest.getPassword()));
        Restaurant restaurant = new Restaurant(restaurantRequest.getEmail(),restaurantRequest.getPhone_number(),restaurantRequest.getRestaurant_name(),
                restaurantRequest.getRestaurant_location(),restaurantRequest.getRestaurant_license(),restaurantRequest.getSocial_media());
        restaurant.setStatus(false);
        userRepository.save(user);
        restaurantRepository.save(restaurant);
        return (restaurant);
    }

    public Patient loginPatient(LoginRequest loginRequest) throws InvalidCredentialsException {

        Patient patient=patientService.getPatientByEmail(loginRequest.getEmail());
        // Validate the password
        if (! (Objects.equals(loginRequest.getPassword(), patient.getUser().getPassword()))||!patient.isStatus() ) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        return patient;
    }

    public Nutritionist loginNutritionist(LoginRequest loginRequest) {
        Nutritionist nutritionist=nutritionistService.getNutritionistByEmail(loginRequest.getEmail());
        // Validate the password
        if (!(Objects.equals(loginRequest.getPassword(), nutritionist.getUser().getPassword()))||!nutritionist.isStatus() ) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        return nutritionist;
    }

    public Restaurant loginRestaurant(LoginRequest loginRequest) {
        Restaurant restaurant=restaurantService.getRestaurantByEmail(loginRequest.getEmail());
        // Validate the password
        if (! (Objects.equals(loginRequest.getPassword(), restaurant.getUser().getPassword()))||!restaurant.isStatus() ) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        return restaurant;
    }


    public void logout(HttpSession session) {
        session.invalidate();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }



}
