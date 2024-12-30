package com.kano.springbootmongoclothesapi.service;

import com.kano.springbootmongoclothesapi.model.User;
import com.kano.springbootmongoclothesapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }


    /**
     * 创建用户/注册用户
     */
    public User createUser(User user) {
        user.setCreatedAt(Instant.now());
        return userRepository.save(user);
    }

    /**
     * 更新用户
     */
    public User updateUser(String id, User user) {
    if (userRepository.existsById(id)) {
            user.setId(id);
            return userRepository.save(user);
    }
        return null;
    }

    /**
     * 删除用户
     */
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}