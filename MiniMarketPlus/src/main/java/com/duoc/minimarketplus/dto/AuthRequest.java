package com.duoc.minimarketplus.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank(message = "El usuario es obligatorio")
        String username,

        @NotBlank(message = "El password es obligatorio")
        String password
) {
}
