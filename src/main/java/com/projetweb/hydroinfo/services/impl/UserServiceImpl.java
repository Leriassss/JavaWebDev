package com.projetweb.hydroinfo.services.impl;

import com.projetweb.hydroinfo.dtos.UserDto;
import com.projetweb.hydroinfo.exceptions.CustomValidationException;
import com.projetweb.hydroinfo.exceptions.EntityNotFoundException;
import com.projetweb.hydroinfo.models.User;
import com.projetweb.hydroinfo.repositories.UserRepository;
import com.projetweb.hydroinfo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    public User createUser(UserDto createUserDto) {
        List<String> validationErrors = new ArrayList<>();
        if (userRepository.existsByEmail(createUserDto.email())) {
            validationErrors.add("Un utilisateur avec cet email existe déjà.");
        }

        if (userRepository.existsByPhoneNumber(createUserDto.phoneNumber())) {
            validationErrors.add("Un utilisateur avec cet numero de téléphone existe déjà.");
        }
        if (!validationErrors.isEmpty()) {
            throw new CustomValidationException(validationErrors);
        }
        User user = User.builder()
                .name(createUserDto.name())
                .phoneNumber(createUserDto.phoneNumber())
                .email(createUserDto.email())
                .build();

        return userRepository.save(user);
    }


    @Override
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User",
                        "id",
                        id.toString()));
    }

    @Override
    public User updateUser(Long id, UserDto createUserDto) {
        List<String> validationErrors = new ArrayList<>();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User",
                        "id",
                        id.toString()));
        if (userRepository.existsByEmail(createUserDto.email())) {
            validationErrors.add("Un utilisateur avec cet email existe déjà.");
        }

        if (userRepository.existsByPhoneNumber(createUserDto.phoneNumber())) {
            validationErrors.add("Un utilisateur avec cet numero de téléphone existe déjà.");
        }

        if (!validationErrors.isEmpty()) {
            throw new CustomValidationException(validationErrors);
        }
        user.setName(createUserDto.name());
        user.setEmail(createUserDto.email());
        user.setPhoneNumber(createUserDto.phoneNumber());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User",
                        "id",
                        id.toString()));
        userRepository.delete(user);
    }

    @Override
    public Page<User> getUsersPage(Pageable pageable) {

        return userRepository.findAll(pageable);
    }

}
