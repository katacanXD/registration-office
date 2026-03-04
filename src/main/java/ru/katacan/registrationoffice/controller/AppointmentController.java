package ru.katacan.registrationoffice.controller;

import ru.katacan.registrationoffice.dto.*;
import ru.katacan.registrationoffice.entity.Appointment;
import ru.katacan.registrationoffice.entity.DictStatus;
import ru.katacan.registrationoffice.entity.User;
import ru.katacan.registrationoffice.mapper.AppointmentMapper;
import ru.katacan.registrationoffice.mapper.UserMapper;
import ru.katacan.registrationoffice.repository.AppointmentRepository;
import ru.katacan.registrationoffice.repository.DictStatusRepository;
import ru.katacan.registrationoffice.repository.UserRepository;
import ru.katacan.registrationoffice.repository.WorkSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final DictStatusRepository statusRepository;
    private final WorkSlotRepository workSlotRepository;
    private final AppointmentMapper appointmentMapper;
    private final UserMapper userMapper;

    @PostMapping("/appointments")
    public ResponseEntity<?> createAppointment(@RequestBody CreateAppointmentRequestDto request) {
        // Проверяем существование врача и пациента
        User doctor = userRepository.findById(request.getDoctorId()).orElse(null);
        User patient = userRepository.findById(request.getPatientId()).orElse(null);

        if (doctor == null || patient == null) {
            return ResponseEntity.badRequest()
                    .body(ErrorResponseDto.builder()
                            .message("Врач или пациент не найден")
                            .build());
        }

        // Проверяем, что слот свободен
        LocalDateTime slotTime = LocalDateTime.parse(request.getSlotDatetime());
        if (appointmentRepository.existsByDoctorAndSlotDateTimeAndNotCanceled(doctor, slotTime)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ErrorResponseDto.builder()
                            .message("Этот слот уже занят")
                            .build());
        }

        // Получаем статус "booked"
        DictStatus status = statusRepository.findByName("booked")
                .orElseThrow(() -> new RuntimeException("Статус booked не найден"));

        // Создаем запись
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setSlotDateTime(slotTime);
        appointment.setStatus(status);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(appointmentMapper.toCreateResponse(savedAppointment, doctor));
    }

    @DeleteMapping("/appointments/{appointmentId}")
    public ResponseEntity<?> cancelAppointment(@PathVariable Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .map(appointment -> {
                    try {
                        // Получаем статус "canceled"
                        DictStatus canceledStatus = statusRepository.findByName("canceled")
                                .orElseThrow(() -> new RuntimeException("Статус canceled не найден"));

                        appointment.setStatus(canceledStatus);
                        Appointment cancelled = appointmentRepository.save(appointment);

                        String speciality = appointment.getDoctor() != null ?
                                appointment.getDoctor().getSpeciality() : "Врач";

                        // Успешный ответ с DTO отмены
                        return ResponseEntity.ok()
                                .body(appointmentMapper.toCancelResponse(cancelled, speciality));

                    } catch (RuntimeException e) {
                        // Ошибка при получении статуса или сохранении
                        return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ErrorResponseDto.builder()
                                        .message("Ошибка при отмене записи: " + e.getMessage())
                                        .build());
                    }
                })
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)  // 404 Not Found, а не 401 Unauthorized
                        .body(ErrorResponseDto.builder()
                                .message("Запись с id " + appointmentId + " не найдена")
                                .build()));
    }

    @GetMapping("/appointments/{appointmentId}")
    public ResponseEntity<AppointmentDetailDto> getAppointment(@PathVariable Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .map(appointment -> {
                    AppointmentDetailDto dto = appointmentMapper.toAppointmentDetail(appointment);

                    // Заполняем информацию о докторе и пациенте
                    if (dto != null) {
                        if (appointment.getDoctor() != null) {
                            dto.setDoctor(userMapper.toDoctorInfo(appointment.getDoctor()));
                        }
                        if (appointment.getPatient() != null) {
                            dto.setPatient(userMapper.toPatientInfo(appointment.getPatient()));
                        }
                    }

                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}