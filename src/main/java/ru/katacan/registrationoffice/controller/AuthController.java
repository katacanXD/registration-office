package ru.katacan.registrationoffice.controller;

import ru.katacan.registrationoffice.dto.*;
import ru.katacan.registrationoffice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponseDto> register(@RequestBody AuthRequestDto request) {
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto request) {
    }
}