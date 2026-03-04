package ru.katacan.registrationoffice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DoctorCalendarDto {
    private List<WorkDayDto> workDays;

    @Data
    @Builder
    public static class WorkDayDto {
        private String date; // "2026-03-24"
        private Boolean hasAppointments;
        private Integer totalSlots;
        private Integer closedSlots;
    }
}