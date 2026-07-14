package com.minimarketplus.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

@Schema(description = "Movimiento de stock registrado en inventario")
public record MovimientoInventario(
        @Schema(example = "1") Long id,
        @Positive @Schema(example = "1") Long productoId,
        @NotBlank @Schema(example = "ENTRADA") String tipo,
        @Positive @Schema(example = "20") Integer cantidad,
        @Schema(example = "Recepcion de proveedor") String descripcion,
        @Schema(example = "2026-07-13T20:30:00") LocalDateTime fecha
) {
}
