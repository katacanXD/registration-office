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

    @GetMapping("/dashboard")
    public ResponseEntity<PatientDashboardDto> getDashboard(@RequestParam Long patientId) {
        return userRepository.findById(patientId)
                .map(patient -> {
                    // Получаем предстоящие записи пациента (не отмененные и не завершенные)
                    List<Appointment> appointments = appointmentRepository
                            .findByPatientAndStatus_NameIn(patient,
                                    List.of("booked")); // только активные

                    return ResponseEntity.ok(PatientDashboardDto.builder()
                            .user(userMapper.toPatientUserInfo(patient))
                            .appointments(appointments.stream()
                                    .map(appointmentMapper::toAppointmentShort)
                                    .collect(Collectors.toList()))
                            .build());
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/policy")
    public ResponseEntity<?> updatePolicy(@RequestParam Long patientId,
                                          @RequestBody PolicyRequestDto request) {
        return userRepository.findById(patientId)
                .<ResponseEntity<?>>map(patient -> {
                    patient.setPolicyNumber(request.getPolicyNumber());
                    userRepository.save(patient);

                    return ResponseEntity.ok(PolicyResponseDto.builder()
                            .success(true)
                            .message("Полис успешно привязан. Теперь вы можете записываться на прием.")
                            .build());
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponseDto.builder()
                                .message("Пациент с id " + patientId + " не найден")
                                .build()));
    }
}