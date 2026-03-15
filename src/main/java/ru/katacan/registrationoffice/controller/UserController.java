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

//    @GetMapping()
//    public ResponseEntity<List<UserInfoDto>> getAllUsers() {
//    }
//
//    /**
//     * Получить пользователя по ID
//     */
//    @GetMapping("/{userId}")
//    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
//    }
//
//    /**
//     * Обновить информацию о пользователе
//     */
//    @PatchMapping("/{userId}")
//    public ResponseEntity<?> updateUser(
//            @PathVariable Long userId,
//            @RequestBody UpdateUserRequestDto request
//    ) {
//    }
//
//    /**
//     * Удалить пользователя
//     */
//    @DeleteMapping("/{userId}")
//    public ResponseEntity<?> deleteUser(
//            @PathVariable Long userId
//    ) {
//    }
//
//    /**
//     * Получить всех врачей
//     */
//    @GetMapping("/doctors")
//    public ResponseEntity<List<DoctorShortDto>> getAllDoctors() {
//    }
//
//    /**
//     * Получить всех пациентов
//     */
//    @GetMapping("/patients")
//    public ResponseEntity<List<PatientListDto.PatientDto>> getAllPatients() {
//    }
//
//    /**
//     * Поиск пользователей по ФИО
//     */
//    @GetMapping("/search")
//    public ResponseEntity<List<UserInfoDto>> searchUsers(@RequestParam String query) {
//    }
//
//    /**
//     * Сменить пароль
//     */
//    @PostMapping("/{userId}/change-password")
//    public ResponseEntity<?> changePassword(
//            @PathVariable Long userId,
//            @RequestBody ChangePasswordRequestDto request) {
//    }
}