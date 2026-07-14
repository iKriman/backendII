package com.minimarketplus.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

@Schema(description = "Producto disponible para venta en Minimarket Plus")
public record Producto(
        @Schema(example = "1") Long id,
        @NotBlank @Schema(example = "Arroz grado 1") String nombre,
        @NotBlank @Schema(example = "Abarrotes") String categoria,
        @Positive @Schema(example = "1890") BigDecimal precio,
        @PositiveOrZero @Schema(example = "35") Integer stock
) {
}
