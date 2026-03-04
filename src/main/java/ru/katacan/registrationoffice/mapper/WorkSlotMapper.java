package ru.katacan.registrationoffice.mapper;

import ru.katacan.registrationoffice.dto.*;
import ru.katacan.registrationoffice.entity.WorkSlot;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WorkSlotMapper {

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

    public DoctorSlotsDto toDoctorSlots(Long doctorId, String date, List<WorkSlot> slots,
                                        DoctorSlotsDto.BreakDto breakDto) {
        List<DoctorSlotsDto.SlotDto> slotDtos = slots.stream()
                .flatMap(slot -> generateTimeSlots(slot).stream())
                .collect(Collectors.toList());

        return DoctorSlotsDto.builder()
                .doctorId(doctorId)
                .date(date)
                .slots(slotDtos)
                .break_(breakDto)
                .build();
    }

    private List<DoctorSlotsDto.SlotDto> generateTimeSlots(WorkSlot slot) {
        // Логика генерации слотов по slotMinutes
        // TODO: реализовать генерацию временных слотов
        return List.of();
    }

    public DoctorScheduleDto toDoctorSchedule(String date, List<WorkSlot> slots,
                                              DoctorScheduleDto.BreakDto breakDto) {
        List<DoctorScheduleDto.ScheduleSlotDto> slotDtos = slots.stream()
                .flatMap(slot -> generateScheduleSlots(slot).stream())
                .collect(Collectors.toList());

        return DoctorScheduleDto.builder()
                .date(date)
                .slots(slotDtos)
                .break_(breakDto)
                .build();
    }

    private List<DoctorScheduleDto.ScheduleSlotDto> generateScheduleSlots(WorkSlot slot) {
        // Логика генерации слотов для расписания
        // TODO: реализовать
        return List.of();
    }

    public DoctorCalendarDto toDoctorCalendar(List<WorkSlot> slots) {
        List<DoctorCalendarDto.WorkDayDto> workDays = slots.stream()
                .collect(Collectors.groupingBy(WorkSlot::getDate))
                .entrySet().stream()
                .map(entry -> {
                    // TODO: рассчитать статистику по дню
                    return DoctorCalendarDto.WorkDayDto.builder()
                            .date(entry.getKey().format(dateFormatter))
                            .hasAppointments(false)
                            .totalSlots(0)
                            .closedSlots(0)
                            .build();
                })
                .collect(Collectors.toList());

        return DoctorCalendarDto.builder()
                .workDays(workDays)
                .build();
    }
}