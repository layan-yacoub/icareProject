package com.example.icare.controller;

import com.example.icare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController //is used in REST Web services & mark class as Controller Class

@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

}



