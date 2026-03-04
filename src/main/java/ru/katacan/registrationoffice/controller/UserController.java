package ru.katacan.registrationoffice.controller;

import ru.katacan.registrationoffice.dto.*;
import ru.katacan.registrationoffice.entity.User;
import ru.katacan.registrationoffice.mapper.UserMapper;
import ru.katacan.registrationoffice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Получить всех пользователей
     */
    @GetMapping
    public ResponseEntity<List<UserInfoDto>> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserInfoDto> userDtos = users.stream()
                .map(user -> UserInfoDto.builder()
                        .userId(user.getId())
                        .fio(user.getFullName())
                        .aclName(user.getAcl() != null ? user.getAcl().getName() : null)
                        .policyNumber(user.getPolicyNumber())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(userDtos);
    }

    /**
     * Получить пользователя по ID
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        return userRepository.findById(userId)
                .<ResponseEntity<?>>map(user -> ResponseEntity.ok(UserInfoDto.builder()
                        .userId(user.getId())
                        .fio(user.getFullName())
                        .aclName(user.getAcl() != null ? user.getAcl().getName() : null)
                        .policyNumber(user.getPolicyNumber())
                        .build()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponseDto.builder()
                                .message("Пользователь с id " + userId + " не найден")
                                .build()));
    }

    /**
     * Получить всех врачей
     */
    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorShortDto>> getAllDoctors() {
        List<User> doctors = userRepository.findAllDoctors();
        List<DoctorShortDto> doctorDtos = doctors.stream()
                .map(userMapper::toDoctorShort)
                .collect(Collectors.toList());

        return ResponseEntity.ok(doctorDtos);
    }

    /**
     * Получить всех пациентов
     */
    @GetMapping("/patients")
    public ResponseEntity<List<PatientListDto.PatientDto>> getAllPatients() {
        List<User> patients = userRepository.findAllPatients();
        List<PatientListDto.PatientDto> patientDtos = patients.stream()
                .map(userMapper::toPatientDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(patientDtos);
    }

    /**
     * Поиск пользователей по ФИО
     */
    @GetMapping("/search")
    public ResponseEntity<List<UserInfoDto>> searchUsers(@RequestParam String query) {
        List<User> users = userRepository.findAll().stream()
                .filter(user -> user.getFullName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());

        List<UserInfoDto> userDtos = users.stream()
                .map(user -> UserInfoDto.builder()
                        .userId(user.getId())
                        .fio(user.getFullName())
                        .aclName(user.getAcl() != null ? user.getAcl().getName() : null)
                        .policyNumber(user.getPolicyNumber())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(userDtos);
    }

    /**
     * Обновить информацию о пользователе
     */
    @PatchMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId,
                                        @RequestBody UpdateUserRequestDto request) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (request.getFio() != null) {
                user.setFullName(request.getFio());
            }
            if (request.getPolicyNumber() != null) {
                user.setPolicyNumber(request.getPolicyNumber());
            }
            if (request.getSpeciality() != null) {
                user.setSpeciality(request.getSpeciality());
            }

            User updatedUser = userRepository.save(user);

            return ResponseEntity.ok(UserInfoDto.builder()
                    .userId(updatedUser.getId())
                    .fio(updatedUser.getFullName())
                    .aclName(updatedUser.getAcl() != null ? updatedUser.getAcl().getName() : null)
                    .policyNumber(updatedUser.getPolicyNumber())
                    .build());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponseDto.builder()
                            .message("Пользователь с id " + userId + " не найден")
                            .build());
        }
    }

    /**
     * Удалить пользователя
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return ResponseEntity.ok(SuccessResponseDto.builder()
                    .success(true)
                    .message("Пользователь успешно удален")
                    .build());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponseDto.builder()
                            .message("Пользователь с id " + userId + " не найден")
                            .build());
        }
    }

    /**
     * Сменить пароль
     */
    @PostMapping("/{userId}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable Long userId,
                                            @RequestBody ChangePasswordRequestDto request) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Проверяем старый пароль
            if (!user.getPassword().equals(request.getOldPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ErrorResponseDto.builder()
                                .message("Неверный текущий пароль")
                                .build());
            }

            // Устанавливаем новый пароль
            user.setPassword(request.getNewPassword());
            userRepository.save(user);

            return ResponseEntity.ok(SuccessResponseDto.builder()
                    .success(true)
                    .message("Пароль успешно изменен")
                    .build());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponseDto.builder()
                            .message("Пользователь с id " + userId + " не найден")
                            .build());
        }
    }
}