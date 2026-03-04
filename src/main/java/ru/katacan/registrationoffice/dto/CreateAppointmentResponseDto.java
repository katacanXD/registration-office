package ru.katacan.registrationoffice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateAppointmentResponseDto {
    private Long appointmentId;
    private String message;
    private AppointmentDetailsDto details;

    @Data
    @Builder
    public static class AppointmentDetailsDto {
        private String doctorFio;
        private String speciality;
        private String address;
    }
}