package ru.katacan.registrationoffice.service;

import ru.katacan.registrationoffice.dto.DoctorCalendarDto;
import ru.katacan.registrationoffice.dto.DoctorSlotsDto;
import ru.katacan.registrationoffice.entity.WorkSlot;

import java.time.LocalDate;
import java.util.List;

public interface WorkSlotService {
    DoctorSlotsDto getDoctorSlots(Long doctorId, LocalDate date);
    DoctorCalendarDto getDoctorSlotsBetweenDates(Long doctorId, LocalDate startDate, LocalDate endDate);
}
