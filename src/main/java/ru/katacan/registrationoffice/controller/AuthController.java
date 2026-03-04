package ru.katacan.registrationoffice.controller;

import ru.katacan.registrationoffice.dto.*;
import ru.katacan.registrationoffice.entity.DictAclName;
import ru.katacan.registrationoffice.entity.User;
import ru.katacan.registrationoffice.mapper.UserMapper;
import ru.katacan.registrationoffice.repository.DictAclNameRepository;
import ru.katacan.registrationoffice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final DictAclNameRepository aclNameRepository;
    private final UserMapper userMapper;

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponseDto> register(@RequestBody AuthRequestDto request) {
        // Проверяем, существует ли уже пользователь
        if (userRepository.findByFullName(request.getFio()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(RegistrationResponseDto.builder()
                            .message("Пользователь с таким ФИО уже существует")
                            .build());
        }

        // Получаем роль "patient" по умолчанию
        DictAclName patientRole = aclNameRepository.findByName("patient")
                .orElseThrow(() -> new RuntimeException("Роль patient не найдена"));

        // Создаем нового пользователя
        User user = new User();
        user.setFullName(request.getFio());
        user.setPassword(request.getPassword()); // В реальности нужно хэшировать!
        user.setAcl(patientRole);

        User savedUser = userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.toRegistrationResponse(savedUser,
                        "Пользователь успешно зарегистрирован"));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto request) {
        // Ищем пользователя по ФИО и паролю (в реальности проверять хэш)
        Optional<User> userOptional = userRepository.findByFullNameAndPassword(request.getFio(), request.getPassword());

        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userMapper.toLoginResponse(userOptional.get(), "dummy-token"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ErrorResponseDto.builder()
                            .message("Неверные учетные данные")
                            .build());
        }
    }
}