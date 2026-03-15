package ru.katacan.registrationoffice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CancelAppointmentResponseDto {
    private Long appointmentId;
    private String status;
    private String message;
}