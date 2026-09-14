package com.projetweb.hydroinfo.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record  ProjectDto(
    @NotBlank(message = "Le nom ne peut pas être vide")
    String name,
    @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères")
    String description,
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime startedAt,
    //@Future(message = "La date saisie doit être un edate dans le future")
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime endedAt) {
}
