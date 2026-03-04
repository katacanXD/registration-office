package ru.katacan.registrationoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.katacan.registrationoffice.entity.Appointment;
import ru.katacan.registrationoffice.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctorAndSlotDateTimeBetween(User doctor, LocalDateTime start, LocalDateTime end);
    List<Appointment> findByPatient(User patient);
    List<Appointment> findByStatusName(String statusName);
}