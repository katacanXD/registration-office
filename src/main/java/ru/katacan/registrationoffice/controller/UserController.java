package ru.katacan.registrationoffice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.katacan.registrationoffice.entity.User;
import ru.katacan.registrationoffice.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserRepository userRepo;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("")
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @GetMapping("/doctors")
    public List<User> getDoctors() {
        return userRepo.findByAclName("doctor");
    }

    @GetMapping("/patient/{policy}")
    public User getPatientByPolicy(@PathVariable String policy) {
        return userRepo.findByPolicyNumber(policy);
    }
}