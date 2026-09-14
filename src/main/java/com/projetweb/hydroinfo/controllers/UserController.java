package com.projetweb.hydroinfo.controllers;

import com.projetweb.hydroinfo.dtos.UserDto;
import com.projetweb.hydroinfo.models.User;
import com.projetweb.hydroinfo.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //Créer un utilisateur
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDto createUserDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(createUserDto));
    }



    // Mettre à jour un utilisateur
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDto createUserDto
    ) {

        User updatedUser = userService.updateUser(id, createUserDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    // Liste des utilisateurs
    @GetMapping
    public ResponseEntity<Page<User>> getUsersByPage(
            @RequestParam int page,
            @RequestParam int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));
        Page<User> Users = userService.getUsersPage(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(Users);
    }

    //Details d'un utilisateur
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long UserId) {
        User User = userService.getUser(UserId);

        return ResponseEntity.status(HttpStatus.OK).body(User);
    }

    // Suppression d'un utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("Utilisateur supprimé avec succès.");
    }



}
