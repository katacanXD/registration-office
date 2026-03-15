package ru.katacan.registrationoffice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoDto {
    private Long userId;
    private String fio;
    private String aclName;
    private String policyNumber;
}