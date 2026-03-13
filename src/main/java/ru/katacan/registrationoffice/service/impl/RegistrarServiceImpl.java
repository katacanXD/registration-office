package ru.katacan.registrationoffice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.katacan.registrationoffice.dto.PatientListDto;
import ru.katacan.registrationoffice.entity.User;
import ru.katacan.registrationoffice.mapper.UserMapper;
import ru.katacan.registrationoffice.repository.UserRepository;
import ru.katacan.registrationoffice.service.RegistrarService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrarServiceImpl implements RegistrarService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<PatientListDto.PatientDto> getAllPatients() {
        List<User> patients = userRepository.findAllPatients();

        return patients.stream()
                .map(userMapper::toPatientDto)
                .collect(Collectors.toList());
    }
}
