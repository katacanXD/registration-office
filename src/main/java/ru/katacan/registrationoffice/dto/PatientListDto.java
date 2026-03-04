package ru.katacan.registrationoffice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PatientListDto {
    private List<PatientDto> patients;

    @Data
    @Builder
    public static class PatientDto {
        private Long userId;
        private String fio;
        private String policyNumber;
    }
}