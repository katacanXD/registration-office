package ru.katacan.registrationoffice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
public class CreateAppointmentRequestDto {
    private Long doctorId;
    private Long patientId;
    private String slotDatetime; // "2026-03-24T13:15"
    private String status;
}