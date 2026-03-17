package ru.katacan.registrationoffice.service;

import ru.katacan.registrationoffice.dto.*;
import ru.katacan.registrationoffice.entity.User;

public interface UserService {
    DoctorListDto searchDoctors(String search);
    PatientListDto searchPatients(String search);
    UserListDto searchUsers(String search);
    User updateUser(Long userId, UpdateUserRequestDto request);
    void deleteUser(Long userId);
    boolean changePassword(Long userId, ChangePasswordRequestDto request);
    User saveUser(User user);
    PatientDashboardDto getDashboard(Long patientId);
    PolicyResponseDto updatePolicy(Long patientId, PolicyRequestDto request);
}