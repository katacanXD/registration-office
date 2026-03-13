package ru.katacan.registrationoffice.service;

import ru.katacan.registrationoffice.dto.AppointmentDetailDto;
import ru.katacan.registrationoffice.dto.CreateAppointmentRequestDto;
import ru.katacan.registrationoffice.entity.Appointment;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Appointment createAppointment(CreateAppointmentRequestDto request);
    void cancelAppointment(Long appointmentId);
    Optional<Appointment> getAppointmentById(Long appointmentId);
    AppointmentDetailDto getDetailAppointmentById(Long appointmentId);
    List<Appointment> getPatientAppointments(Long patientId, List<String> statuses);
    boolean isSlotAvailable(Long doctorId, String slotDateTime);
}