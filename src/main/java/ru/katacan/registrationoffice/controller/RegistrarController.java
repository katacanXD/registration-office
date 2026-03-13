package ru.katacan.registrationoffice.controller;

import ru.katacan.registrationoffice.dto.PatientListDto;
import ru.katacan.registrationoffice.mapper.UserMapper;
import ru.katacan.registrationoffice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.katacan.registrationoffice.service.RegistrarService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/registration")
@RequiredArgsConstructor
public class RegistrarController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final RegistrarService registrarService;

    @GetMapping("/patients")
    public ResponseEntity<PatientListDto> getAllPatients() { //TODO добавить плагинацию
        List<PatientListDto.PatientDto> patients = registrarService.getAllPatients();

        PatientListDto response = new PatientListDto();
        response.setPatients(patients);

        return ResponseEntity.ok(response);
    }
}