package ru.katacan.registrationoffice.service;

import ru.katacan.registrationoffice.dto.ChangePasswordRequestDto;
import ru.katacan.registrationoffice.dto.UpdateUserRequestDto;
import ru.katacan.registrationoffice.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long userId);
    List<User> getAllDoctors();
    List<User> getAllPatients();
    List<User> searchDoctors(String search);
    List<User> searchUsers(String query);
    User updateUser(Long userId, UpdateUserRequestDto request);
    void deleteUser(Long userId);
    boolean changePassword(Long userId, ChangePasswordRequestDto request);
    User saveUser(User user);
}