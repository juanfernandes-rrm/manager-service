package com.tads.manager.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateManagerDTO(
    @NotBlank
    String name,
    @NotBlank @Email
    String email,
    @NotNull @Valid @Digits(integer = 13, fraction = 0)
    Integer phonenumber
)
{}