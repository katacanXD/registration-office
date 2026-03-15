package ru.katacan.registrationoffice.controller;

import ru.katacan.registrationoffice.dto.*;
import ru.katacan.registrationoffice.entity.Appointment;
import ru.katacan.registrationoffice.entity.User;
import ru.katacan.registrationoffice.mapper.AppointmentMapper;
import ru.katacan.registrationoffice.mapper.UserMapper;
import ru.katacan.registrationoffice.repository.AppointmentRepository;
import ru.katacan.registrationoffice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/patient")
@RequiredArgsConstructor
public class PatientController {

    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserMapper userMapper;
    private final AppointmentMapper appointmentMapper;

//    @GetMapping("/dashboard")
//    public ResponseEntity<PatientDashboardDto> getDashboard(@RequestParam Long patientId) {
//    }
//
//    @PatchMapping("/policy")
//    public ResponseEntity<?> updatePolicy(@RequestParam Long patientId,
//                                          @RequestBody PolicyRequestDto request) {
//    }
}