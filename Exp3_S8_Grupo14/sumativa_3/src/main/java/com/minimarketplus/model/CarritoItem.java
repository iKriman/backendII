package com.minimarketplus.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Schema(description = "Producto agregado al carrito de compra")
public record CarritoItem(
        @Schema(example = "1") Long id,
        @Positive @Schema(example = "1") Long productoId,
        @NotBlank @Schema(example = "Arroz grado 1") String nombreProducto,
        @Positive @Schema(example = "2") Integer cantidad
) {
}
