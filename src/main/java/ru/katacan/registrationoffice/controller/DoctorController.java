package ru.katacan.registrationoffice.controller;

import ru.katacan.registrationoffice.dto.*;
import ru.katacan.registrationoffice.entity.User;
import ru.katacan.registrationoffice.entity.WorkSlot;
import ru.katacan.registrationoffice.mapper.UserMapper;
import ru.katacan.registrationoffice.mapper.WorkSlotMapper;
import ru.katacan.registrationoffice.service.UserService;
import ru.katacan.registrationoffice.service.WorkSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final UserService userService;
    private final WorkSlotService workSlotService;
    private final UserMapper userMapper;
    private final WorkSlotMapper workSlotMapper;

    @GetMapping()
    public ResponseEntity<DoctorListDto> getDoctors(
            @RequestParam(required = false) String search) {
    }

    @GetMapping("/{doctorId}/slots")
    public ResponseEntity<DoctorSlotsDto> getDoctorSlots(
            @PathVariable Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
    }

    @GetMapping("/{doctorId}/calendar")
    public ResponseEntity<DoctorCalendarDto> getDoctorCalendar(
            @PathVariable Long doctorId,
            @RequestParam Integer month,
            @RequestParam Integer year) {
    }

    @GetMapping("/{doctorId}/schedule")
    public ResponseEntity<DoctorScheduleDto> getDoctorSchedule(
            @PathVariable Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
    }
}