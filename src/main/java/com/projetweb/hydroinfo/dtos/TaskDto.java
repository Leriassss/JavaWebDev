package com.projetweb.hydroinfo.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record TaskDto(
    @NotBlank(message = "Le nom ne peut pas être vide")
    String name,
    @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères")
    String description,
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime startedAt,
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime endedAt,

    Long projectId,

    Long userId,

    String status){
}
