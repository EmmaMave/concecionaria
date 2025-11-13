package com.example.concecionaria.service;

import com.example.concecionaria.dto.UserRequest;
import com.example.concecionaria.dto.UserResponse;
import com.example.concecionaria.exception.ResourceConflictException;
import com.example.concecionaria.exception.ResourceNotFoundException;
import com.example.concecionaria.model.Role;
import com.example.concecionaria.model.User;
import com.example.concecionaria.repository.RoleRepository;
import com.example.concecionaria.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse createUser(UserRequest request) {
        if (userRepository.findByUserName(request.getUserName()).isPresent()) {
            throw new ResourceConflictException("Username already exists: " + request.getUserName());
        }

        Role userRole = roleRepository.findByRolName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", "ROLE_USER"));

        User user = new User();
        user.setUserName(request.getUserName());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRoles(Set.of(userRole));

        User savedUser = userRepository.save(user);
        return convertToResponse(savedUser);
    }


    @Transactional()
    public UserResponse getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return convertToResponse(user);
    }

    @Transactional
    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        userRepository.deleteById(id);
    }

    private UserResponse convertToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUserName(user.getUserName());
        response.setRoles(
                user.getRoles().stream()
                        .map(Role::getRolName)
                        .collect(Collectors.toSet())
        );
        return response;
    }


}