package com.projetweb.hydroinfo.services;


import com.projetweb.hydroinfo.dtos.UserDto;
import com.projetweb.hydroinfo.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User createUser(UserDto createUserDto);

    User getUser(Long id);

    User updateUser(Long id, UserDto createUserDto);

    void deleteUser(Long id);

    Page<User> getUsersPage(Pageable pageable);

}
