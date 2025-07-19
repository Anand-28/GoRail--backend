package com.railway.userservice.service;

import com.railway.userservice.entity.User;
import com.railway.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

   

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Set default role
        if (user.getRole() == null) {
            user.setRole("USER");
        }

        return userRepository.save(user);
    }  

}

