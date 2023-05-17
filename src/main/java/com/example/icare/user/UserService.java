package com.example.icare.user;
import com.example.icare.domain.Nutritionist;
import com.example.icare.domain.Patient;
import com.example.icare.domain.Restaurant;
import com.example.icare.registrationRequest.*;
import com.example.icare.repository.NutritionistRepository;
import com.example.icare.repository.PatientRepository;
import com.example.icare.repository.RestaurantRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserService   {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final NutritionistRepository nutritionistRepository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;

    public void signupPatient(UserSignupRequest signupRequest , PatientRequest patientRequest) {
        User user = new User(signupRequest.getFirstName(), signupRequest.getLastName(),
                signupRequest.getEmail(), passwordEncoder.encode(signupRequest.getPassword()), "PATIENT");
        Patient patient=new Patient( patientRequest.getFirstName(), patientRequest.getLastName(),patientRequest.getDob(), patientRequest.getCity(), patientRequest.getGender(),
                patientRequest.getWeight(), patientRequest.getHeight(), patientRequest.getDisease(), patientRequest.getLifestyle());
        user.setStatus(true);
        patient.setStatues(true);
        userRepository.save(user);
        patientRepository.save(patient);

    }

    public void signupNutritionist(UserSignupRequest signupRequest, NutritionistRequest nutritionistRequest) {
        User user = new User(signupRequest.getFirstName(), signupRequest.getLastName(),
                signupRequest.getEmail(), passwordEncoder.encode(signupRequest.getPassword()), "NUTRITIONIST");
        Nutritionist nutritionist = new Nutritionist(nutritionistRequest.getFirstName(),nutritionistRequest.getLastName(),nutritionistRequest.getLocation(),nutritionistRequest.getCenterName(),
                nutritionistRequest.getCenterLicense(),nutritionistRequest.getNutritionistLicense(),nutritionistRequest.getExperience());
        user.setStatus(false);
        nutritionist.setStatues(false);
        userRepository.save(user);
        nutritionistRepository.save(nutritionist);
    }

    public void signupRestaurant(UserSignupRequest signupRequest, RestaurantRequest restaurantRequest) {
        User user = new User(signupRequest.getFirstName(), signupRequest.getLastName(),
                signupRequest.getEmail(), passwordEncoder.encode(signupRequest.getPassword()), "RESTAURANT");
        Restaurant restaurant = new Restaurant(restaurantRequest.getPhone_number(),restaurantRequest.getRestaurant_name(),
                restaurantRequest.getRestaurant_location(),restaurantRequest.getRestaurant_license(),restaurantRequest.getSocial_media());
        user.setStatus(false);
        restaurant.setStatues(false);
        userRepository.save(user);
        restaurantRepository.save(restaurant);
    }

    public String login(LoginRequest loginRequest) throws InvalidCredentialsException {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null || !user.getPassword().equals(loginRequest.getPassword()) || !user.isStatus()) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String redirectUrl = switch (user.getRole()) {
            case PATIENT -> "/patient/dashboard";
            case NUTRITIONIST -> "/nutritionist/dashboard";
            case RESTAURANT -> "/restaurant/dashboard";
            case ADMIN -> "/admin/dashboard";
            default -> throw new InvalidCredentialsException("Unknown role");
        };
        return "redirect:" + redirectUrl;
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }

    public void changeEmail(Long userId, String password, String newEmail)
            throws UserNotFoundException, InvalidPasswordException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new InvalidPasswordException("Invalid password");
        }

        user.setEmail(newEmail);
        userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
