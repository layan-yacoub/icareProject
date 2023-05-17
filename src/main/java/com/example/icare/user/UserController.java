package com.example.icare.user;

import com.example.icare.domain.Nutritionist;
import com.example.icare.registrationRequest.*;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
@AllArgsConstructor
@RestController //is used in REST Web services & mark class as Controller Class

@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @PostMapping("/signup/patient")// PATIENT SIGNUP
    public ResponseEntity<String> signupPatient(@RequestBody UserSignupRequest signupRequest, @RequestBody PatientRequest patientRequest) {
        userService.signupPatient(signupRequest ,patientRequest);
        return ResponseEntity.ok("Patient signup successful");
    }

    @PostMapping("/signup/nutritionist") // NUTRITIONIST SIGNUP
    public ResponseEntity<String> signupNutritionist(@RequestBody UserSignupRequest signupRequest , @RequestBody NutritionistRequest nutritionistRequest) {
        userService.signupNutritionist(signupRequest,nutritionistRequest);
        return ResponseEntity.ok("Nutritionist signup successful");
    }

    @PostMapping("/signup/restaurant") //RESTAURANT SIGNUP
    public ResponseEntity<String> signupRestaurant(@RequestBody UserSignupRequest signupRequest , @RequestBody RestaurantRequest restaurantRequest) {
        userService.signupRestaurant(signupRequest,restaurantRequest);
        return ResponseEntity.ok("Restaurant signup successful");
    }

    @PostMapping("/login") //LOGIN
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            String redirectUrl = userService.login(loginRequest);
            return ResponseEntity.ok(redirectUrl);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @PostMapping("/logout") //LOGOUT
    public ResponseEntity<String> logout(HttpSession session) {
        userService.logout(session);
        return ResponseEntity.ok("Logout successful");
    }

    @PutMapping("/{user_id}/email") //change email
    public ResponseEntity<String> changeEmail(@PathVariable("user_id") Long userId,
                                              @RequestParam String password,
                                              @RequestParam String newEmail) {
        try {
            userService.changeEmail(userId, password, newEmail);
            return ResponseEntity.ok("Email changed successfully");
        } catch (InvalidPasswordException e) {
            return ResponseEntity.badRequest().body("Invalid password");
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}



