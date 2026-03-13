package ru.katacan.registrationoffice.service.impl;

import ru.katacan.registrationoffice.dto.AuthRequestDto;
import ru.katacan.registrationoffice.dto.LoginResponseDto;
import ru.katacan.registrationoffice.dto.RegistrationResponseDto;
import ru.katacan.registrationoffice.entity.DictAclName;
import ru.katacan.registrationoffice.entity.User;
import ru.katacan.registrationoffice.mapper.UserMapper;
import ru.katacan.registrationoffice.repository.DictAclNameRepository;
import ru.katacan.registrationoffice.repository.UserRepository;
import ru.katacan.registrationoffice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final DictAclNameRepository aclNameRepository;
    private final UserMapper userMapper;

    @Override
    public RegistrationResponseDto registerUser(AuthRequestDto request) {
        if (userRepository.findByFullName(request.getFio()).isPresent()) {
            throw new RuntimeException("Пользователь с таким ФИО уже существует");
        }

        DictAclName patientRole = aclNameRepository.findByName("patient")
                .orElseThrow(() -> new RuntimeException("Роль patient не найдена"));

        User user = new User();
        user.setFullName(request.getFio());
        user.setPassword(request.getPassword()); // В реальности нужно хэшировать!
        user.setAcl(patientRole);

        User savedUser = userRepository.save(user);

        return userMapper.toRegistrationResponse(savedUser,
                "Пользователь успешно зарегистрирован");
    }

    @Override
    public LoginResponseDto loginUser(AuthRequestDto request) {
        Optional<User> userOptional = userRepository.findByFullNameAndPassword(
                request.getFio(), request.getPassword());

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Неверные учетные данные");
        }

        return userMapper.toLoginResponse(userOptional.get(), "dummy-token");
    }
}