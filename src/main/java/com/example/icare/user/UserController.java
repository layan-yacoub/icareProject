package com.example.icare.user;

import com.example.icare.domain.Nutritionist;
import com.example.icare.domain.Patient;
import com.example.icare.domain.Restaurant;
import com.example.icare.registrationRequest.*;
import com.example.icare.service.NutritionistService;
import com.example.icare.service.PatientService;
import com.example.icare.service.RestaurantService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@AllArgsConstructor
@RestController //is used in REST Web services & mark class as Controller Class

@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PatientService patientService;
    private final NutritionistService nutritionistService;
    private final RestaurantService restaurantService;

    @PostMapping("/signup/patient")// PATIENT SIGNUP
    public ResponseEntity<Patient> signupPatient(@RequestBody PatientRequest patientRequest) {
        Patient patient= userService.signupPatient(patientRequest);
        return ResponseEntity.ok(patient);
    }

    @PostMapping("/signup/nutritionist") // NUTRITIONIST SIGNUP
    public ResponseEntity<Nutritionist> signupNutritionist(@RequestBody NutritionistRequest nutritionistRequest) {
      Nutritionist nutritionist=  userService.signupNutritionist(nutritionistRequest);
        return ResponseEntity.ok(nutritionist);
    }

    @PostMapping("/signup/restaurant") //RESTAURANT SIGNUP
    public ResponseEntity<Restaurant> signupRestaurant(@RequestBody RestaurantRequest restaurantRequest) {
      Restaurant restaurant=  userService.signupRestaurant(restaurantRequest);
        return ResponseEntity.ok(restaurant);
    }

    @PostMapping("/login") //LOGIN
    public ResponseEntity<?>login(@RequestBody LoginRequest loginRequest) {
        try {
            if(patientService.getPatientByEmail(loginRequest.getEmail())!=null){
            Patient patient=userService.loginPatient(loginRequest);
                return ResponseEntity.ok(patient);
            }
            else if (nutritionistService.getNutritionistByEmail(loginRequest.getEmail())!=null){
                Nutritionist nutritionist=userService.loginNutritionist(loginRequest);
                return ResponseEntity.ok(nutritionist);

            } else if (restaurantService.getRestaurantByEmail(loginRequest.getEmail())!=null) {
                Restaurant restaurant=userService.loginRestaurant(loginRequest);
                return ResponseEntity.ok(restaurant);
            }
            else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

         catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

    }

    @PostMapping("/logout") //LOGOUT
    public ResponseEntity<String> logout(HttpSession session) {
        userService.logout(session);
        return ResponseEntity.ok("Logout successful");
    }



}



