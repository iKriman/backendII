package com.minimarketplus.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Usuario registrado con rol de acceso")
public record Usuario(
        @Schema(example = "1") Long id,
        @NotBlank @Schema(example = "Camila Soto") String nombre,
        @Email @NotBlank @Schema(example = "camila@correo.cl") String email,
        @NotBlank @Schema(example = "CLIENTE") String rol
) {
}
