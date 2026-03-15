package ru.katacan.registrationoffice.service.impl;

import ru.katacan.registrationoffice.dto.*;
import ru.katacan.registrationoffice.entity.User;
import ru.katacan.registrationoffice.exception.ResourceNotFoundException;
import ru.katacan.registrationoffice.mapper.UserMapper;
import ru.katacan.registrationoffice.repository.UserRepository;
import ru.katacan.registrationoffice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public DoctorListDto getAllDoctors() {
        List<User> users = userRepository.findAllDoctors();

        // Преобразуем список пользователей в список DTO
        List<DoctorShortDto> doctorDtos = users.stream()
                .map(userMapper::toDoctorShort)
                .collect(Collectors.toList());

        // Возвращаем объект-обертку DoctorListDto
        return userMapper.toDoctorList(doctorDtos);
    }

    @Override
    public PatientListDto getAllPatients() {
        List<User> users = userRepository.findAll();

        List<PatientListDto.PatientDto> patientDtos = users.stream()
                .map(userMapper::toPatientDto)
                .collect(Collectors.toList());

        return userMapper.toPatientList(patientDtos);
    }

    @Override
    public DoctorListDto searchDoctors(String search) {
        // Вызываем нужный метод репозитория в зависимости от наличия поисковой строки
        if (search == null || search.isEmpty()) {
            return getAllDoctors();
        }
        List<User> users = userRepository.searchDoctors(search);

        // Преобразуем список пользователей в список DTO
        List<DoctorShortDto> doctorDtos = users.stream()
                .map(userMapper::toDoctorShort)
                .collect(Collectors.toList());

        // Возвращаем объект-обертку DoctorListDto
        return userMapper.toDoctorList(doctorDtos);
    }

    @Override
    public List<User> searchUsers(String query) {
        return userRepository.findAll().stream()
                .filter(user -> user.getFullName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public User updateUser(Long userId, UpdateUserRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь с id " + userId + " не найден"));

        if (request.getFio() != null) {
            user.setFullName(request.getFio());
        }
        if (request.getPolicyNumber() != null) {
            user.setPolicyNumber(request.getPolicyNumber());
        }
        if (request.getSpeciality() != null) {
            user.setSpeciality(request.getSpeciality());
        }

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("Пользователь с id " + userId + " не найден");
        }
        userRepository.deleteById(userId);
    }

    @Override
    public boolean changePassword(Long userId, ChangePasswordRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));

        // Если ты еще не внедрил BCrypt, оставляем сравнение строк
        if (!user.getPassword().equals(request.getOldPassword())) {
            return false;
        }

        user.setPassword(request.getNewPassword());
        userRepository.save(user);
        return true;
    }


    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}