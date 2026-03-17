package ru.katacan.registrationoffice.controller;

import org.springframework.http.HttpStatus;
import ru.katacan.registrationoffice.dto.*;
import ru.katacan.registrationoffice.mapper.AppointmentMapper;
import ru.katacan.registrationoffice.mapper.UserMapper;
import ru.katacan.registrationoffice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.katacan.registrationoffice.service.UserService;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final AppointmentMapper appointmentMapper;

    @GetMapping("/dashboard")
    public ResponseEntity<PatientDashboardDto> getDashboard(
            @RequestParam Long patientId
    ) {
        return ResponseEntity.ok(
                userService.getDashboard(patientId));
    }

    @PatchMapping("/policy")
    public ResponseEntity<?> updatePolicy(
            @RequestParam Long patientId,
            @RequestBody PolicyRequestDto request
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.updatePolicy(patientId, request));
    }

    @GetMapping("/patients")
    public ResponseEntity<PatientListDto> getAllPatients(
            @RequestParam(required = false) String search
    ) { //TODO добавить плагинацию
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.searchPatients(search));
    }
}