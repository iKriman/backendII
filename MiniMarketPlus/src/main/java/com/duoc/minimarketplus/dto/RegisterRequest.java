package com.duoc.minimarketplus.dto;

import com.duoc.minimarketplus.entity.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record RegisterRequest(
        @NotBlank(message = "El usuario es obligatorio")
        String username,

        @NotBlank(message = "El password es obligatorio")
        @Size(min=6, message = "Debe tener al menos 6 caracteres")
        String password,

        @NotBlank(message = "El nombre completo es obligatorio")
        String nombreCompleto,

        Set<Rol> roles
) {
}
