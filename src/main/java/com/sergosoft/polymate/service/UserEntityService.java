package com.sergosoft.polymate.service;

import com.sergosoft.polymate.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserEntityService {
    UserEntity saveUser(UserEntity user);
    Optional<UserEntity> getUserById(Long id);
    List<UserEntity> getAllUsers();
    UserEntity updateUser(Long id, UserEntity user);
    void deleteUser(Long id);
    UserEntity updateCurrentBottleBalance(Long id, int newBalance);
    boolean userExistsByEmail(String email);
    boolean userExistsByUsername(String username);
}
