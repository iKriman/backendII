package cl.duoc.minimarket.inventory;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank String name,
        @NotBlank String category,
        @NotBlank String branch,
        @Min(0) int stock,
        @Min(0) int minStock,
        @NotNull BigDecimal price) {
}
