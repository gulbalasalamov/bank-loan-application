package com.gulbalasalamov.bankloanapplication.service;

import com.gulbalasalamov.bankloanapplication.exception.CustomJwtException;
import com.gulbalasalamov.bankloanapplication.exception.EntityNotFoundException;
import com.gulbalasalamov.bankloanapplication.model.Role;
import com.gulbalasalamov.bankloanapplication.model.entity.User;
import com.gulbalasalamov.bankloanapplication.repository.UserRepository;
import com.gulbalasalamov.bankloanapplication.security.JwtTokenProvider;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
@RequiredArgsConstructor

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;


    public List<User> getAll() {
        return userRepository.findAll();
    }

    public String signIn(String userName, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
            return jwtTokenProvider.createToken(userName, userRepository.findByUsername(userName).getRoles());
        } catch (AuthenticationException e) {
            throw new CustomJwtException("Invalid username/password supplied", HttpStatus.BAD_REQUEST);
        }
    }

    public String signUp(User user, boolean isAdmin) {
        if (!userRepository.existsByUsername(user.getUsername())){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Role role = isAdmin ? Role.ROLE_ADMIN : Role.ROLE_CLIENT;
            user.setRoles(Collections.singletonList(role));
            userRepository.save(user);
            return jwtTokenProvider.createToken(user.getUsername(),user.getRoles());
        } else {
            throw new CustomJwtException("Username is already in use",HttpStatus.BAD_REQUEST);
        }
    }

    public void delete(String userName) {
        User byUsername = userRepository.findByUsername(userName);
        if (byUsername == null) {
            throw new EntityNotFoundException("User", "userName : " + userName);
        } else if (byUsername.getRoles().contains(Role.ROLE_ADMIN)) {
            throw new AccessDeniedException("No permission to delete user : " + userName);
        }
        userRepository.deleteByUsername(userName);
    }

    public User search(String userName) {
        User user = userRepository.findByUsername(userName);
        if (user == null) {
            throw new CustomJwtException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user;
    }
}

