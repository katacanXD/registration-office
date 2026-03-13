package ru.katacan.registrationoffice.service;

import ru.katacan.registrationoffice.dto.AuthRequestDto;
import ru.katacan.registrationoffice.dto.LoginResponseDto;
import ru.katacan.registrationoffice.dto.RegistrationResponseDto;

public interface AuthService {
    RegistrationResponseDto registerUser(AuthRequestDto request);
    LoginResponseDto loginUser(AuthRequestDto request);
}
