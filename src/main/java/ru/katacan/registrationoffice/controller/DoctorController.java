package ru.katacan.registrationoffice.controller;

import ru.katacan.registrationoffice.dto.*;
import ru.katacan.registrationoffice.entity.User;
import ru.katacan.registrationoffice.entity.WorkSlot;
import ru.katacan.registrationoffice.mapper.UserMapper;
import ru.katacan.registrationoffice.mapper.WorkSlotMapper;
import ru.katacan.registrationoffice.repository.AppointmentRepository;
import ru.katacan.registrationoffice.repository.UserRepository;
import ru.katacan.registrationoffice.repository.WorkSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DoctorController {

    private final UserRepository userRepository;
    private final WorkSlotRepository workSlotRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserMapper userMapper;
    private final WorkSlotMapper workSlotMapper;

    @GetMapping("/doctors")
    public ResponseEntity<DoctorListDto> getDoctors(
            @RequestParam(required = false) String search) {

        List<User> doctors;
        if (search != null && !search.isEmpty()) {
            doctors = userRepository.searchDoctors(search);
        } else {
            doctors = userRepository.findAllDoctors();
        }

        List<DoctorShortDto> doctorDtos = doctors.stream()
                .map(userMapper::toDoctorShort)
                .collect(Collectors.toList());

        return ResponseEntity.ok(DoctorListDto.builder()
                .doctors(doctorDtos)
                .totalFound((long) doctorDtos.size())
                .build());
    }

    @GetMapping("/doctors/{doctorId}/slots")
    public ResponseEntity<DoctorSlotsDto> getDoctorSlots(
            @PathVariable Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return userRepository.findById(doctorId)
                .map(doctor -> {
                    List<WorkSlot> workSlots = workSlotRepository
                            .findByDoctorAndDate(doctor, date);

                    // TODO: определить перерыв из WorkSlot.breakStart/breakEnd
                    DoctorSlotsDto.BreakDto breakDto = null;

                    return ResponseEntity.ok(workSlotMapper.toDoctorSlots(
                            doctorId, date.toString(), workSlots, breakDto));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/doctor/calendar")
    public ResponseEntity<DoctorCalendarDto> getDoctorCalendar(
            @RequestParam Long doctorId,
            @RequestParam Integer month,
            @RequestParam Integer year) {

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        List<WorkSlot> workSlots = workSlotRepository
                .findByDoctorAndDateBetween(userRepository.getReferenceById(doctorId),
                        startDate, endDate);

        return ResponseEntity.ok(workSlotMapper.toDoctorCalendar(workSlots));
    }

    @GetMapping("/doctor/schedule")
    public ResponseEntity<DoctorScheduleDto> getDoctorSchedule(
            @RequestParam Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return userRepository.findById(doctorId)
                .map(doctor -> {
                    List<WorkSlot> workSlots = workSlotRepository
                            .findByDoctorAndDate(doctor, date);

                    // TODO: определить перерыв
                    DoctorScheduleDto.BreakDto breakDto = null;

                    return ResponseEntity.ok(workSlotMapper.toDoctorSchedule(
                            date.toString(), workSlots, breakDto));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}