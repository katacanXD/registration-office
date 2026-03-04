package ru.katacan.registrationoffice.controller;

import ru.katacan.registrationoffice.dto.PatientListDto;
import ru.katacan.registrationoffice.entity.User;
import ru.katacan.registrationoffice.mapper.UserMapper;
import ru.katacan.registrationoffice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/registration")
@RequiredArgsConstructor
public class RegistrarController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping("/patients")
    public ResponseEntity<PatientListDto> getAllPatients() {
        List<User> patients = userRepository.findAllPatients();

        List<PatientListDto.PatientDto> patientDtos = patients.stream()
                .map(userMapper::toPatientDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(PatientListDto.builder()
                .patients(patientDtos)
                .build());
    }
}