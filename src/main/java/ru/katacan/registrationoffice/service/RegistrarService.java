package ru.katacan.registrationoffice.service;

import ru.katacan.registrationoffice.dto.PatientListDto;

import java.util.List;

public interface RegistrarService {
    List<PatientListDto.PatientDto> getAllPatients();
}
