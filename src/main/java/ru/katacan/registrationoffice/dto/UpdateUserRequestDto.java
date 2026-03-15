package ru.katacan.registrationoffice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
public class UpdateUserRequestDto {
    private String fio;
    private String policyNumber;
    private String speciality;
}