package com.example.icare.service;
import com.example.icare.user.User;
//import com.example.icare.registration.token.ConfirmationToken;
//import com.example.icare.registration.token.ConfirmationTokenService;
import com.example.icare.repository.RoleRepository;
import com.example.icare.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserService   {
    @Autowired
    private UserRepository userRepository;


    public  List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        return new ArrayList<>(users);
    }


    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}


