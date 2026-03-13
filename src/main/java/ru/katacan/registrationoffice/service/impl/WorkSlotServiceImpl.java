package ru.katacan.registrationoffice.service.impl;

import ru.katacan.registrationoffice.dto.DoctorSlotsDto;
import ru.katacan.registrationoffice.entity.User;
import ru.katacan.registrationoffice.entity.WorkSlot;
import ru.katacan.registrationoffice.exception.ResourceNotFoundException;
import ru.katacan.registrationoffice.repository.UserRepository;
import ru.katacan.registrationoffice.repository.WorkSlotRepository;
import ru.katacan.registrationoffice.service.WorkSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkSlotServiceImpl implements WorkSlotService {

    private final WorkSlotRepository workSlotRepository;
    private final UserRepository userRepository;

    @Override
    public List<WorkSlot> getDoctorSlots(Long doctorId, LocalDate date) {
        User doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Врач не найден"));
        return workSlotRepository.findByDoctorAndDate(doctor, date);
    }

    @Override
    public List<WorkSlot> getDoctorSlotsBetweenDates(Long doctorId, LocalDate startDate, LocalDate endDate) {
        User doctor = userRepository.getReferenceById(doctorId);
        return workSlotRepository.findByDoctorAndDateBetween(doctor, startDate, endDate);
    }

    @Override
    public DoctorSlotsDto.BreakDto getBreakForDoctor(Long doctorId, LocalDate date) {
        List<WorkSlot> slots = getDoctorSlots(doctorId, date);

        // TODO: Реализовать логику определения перерыва из WorkSlot.breakStart/breakEnd
        // Например, найти первый слот с перерывом и создать BreakDto
        return slots.stream()
                .filter(slot -> slot.getBreakStart() != null && slot.getBreakEnd() != null)
                .findFirst()
                .map(slot -> DoctorSlotsDto.BreakDto.builder()
                        .start(slot.getBreakStart().toString())
                        .end(slot.getBreakEnd().toString())
                        .build())
                .orElse(null);
    }
}