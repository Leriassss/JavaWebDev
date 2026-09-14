package com.projetweb.hydroinfo.dtos;


import jakarta.validation.constraints.*;

public record UserDto (
    @NotBlank(message = "Le nom ne peut pas être vide")
    String name,
    @NotNull
    @Email
    String email,
    @NotNull
    @Pattern(regexp = "^01[0-9]{8}$", message = "Le numéro de téléphone doit être une suite de 10 chiffres commençant par 01")
    String phoneNumber){

}
