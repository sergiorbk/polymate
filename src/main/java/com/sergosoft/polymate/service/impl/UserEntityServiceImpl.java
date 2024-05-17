package com.sergosoft.polymate.service.impl;

import com.sergosoft.polymate.model.UserEntity;
import com.sergosoft.polymate.repository.UserRepository;
import com.sergosoft.polymate.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserEntityServiceImpl implements UserEntityService {

    private final UserRepository userRepository;

    @Override
    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity updateUser(Long id, UserEntity updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            user.setRole(updatedUser.getRole());
            user.setUsername(updatedUser.getUsername());
            user.setLifetimeBottlesCollected(updatedUser.getLifetimeBottlesCollected());
            user.setCurrentBottleBalance(updatedUser.getCurrentBottleBalance());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserEntity updateCurrentBottleBalance(Long id, int newBalance) {
        return userRepository.findById(id).map(user -> {
            if (newBalance >= 0) {
                user.setCurrentBottleBalance(newBalance);
                return userRepository.save(user);
            } else {
                throw new IllegalArgumentException("New balance cannot be negative");
            }
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean userExistsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

}
