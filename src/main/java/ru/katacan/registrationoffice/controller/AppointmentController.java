package ru.katacan.registrationoffice.controller;

import ru.katacan.registrationoffice.dto.*;
import ru.katacan.registrationoffice.entity.Appointment;
import ru.katacan.registrationoffice.entity.User;
import ru.katacan.registrationoffice.mapper.AppointmentMapper;
import ru.katacan.registrationoffice.mapper.UserMapper;
import ru.katacan.registrationoffice.dto.*;
import ru.katacan.registrationoffice.service.AppointmentService;
import ru.katacan.registrationoffice.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;
import ru.katacan.registrationoffice.service.impl.AppointmentServiceImpl;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentServiceImpl appointmentService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createAppointment(
            @RequestBody CreateAppointmentRequestDto appointmentRequestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(appointmentService.createAppointment(appointmentRequestDto));
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<?> cancelAppointment(
            @PathVariable Long appointmentId
    ) {
        appointmentService.cancelAppointment(appointmentId);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/detail/{appointmentId}")
    public ResponseEntity<AppointmentDetailDto> getAppointment(
            @PathVariable Long appointmentId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(appointmentService.getDetailAppointmentById(appointmentId));
    }
}