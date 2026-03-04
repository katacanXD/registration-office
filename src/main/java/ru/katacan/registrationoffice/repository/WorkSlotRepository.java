package ru.katacan.registrationoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.katacan.registrationoffice.entity.User;
import ru.katacan.registrationoffice.entity.WorkSlot;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkSlotRepository extends JpaRepository<WorkSlot, Long> {
    List<WorkSlot> findByDoctorAndDateBetween(User doctor, LocalDate startDate, LocalDate endDate);
    List<WorkSlot> findByDoctor(User doctor);
}