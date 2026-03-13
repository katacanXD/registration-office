package ru.katacan.registrationoffice.service;

import ru.katacan.registrationoffice.dto.DoctorSlotsDto;
import ru.katacan.registrationoffice.entity.WorkSlot;

import java.time.LocalDate;
import java.util.List;

public interface WorkSlotService {
    List<WorkSlot> getDoctorSlots(Long doctorId, LocalDate date);
    List<WorkSlot> getDoctorSlotsBetweenDates(Long doctorId, LocalDate startDate, LocalDate endDate);
    DoctorSlotsDto.BreakDto getBreakForDoctor(Long doctorId, LocalDate date);
}
