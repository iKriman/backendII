package cl.duoc.minimarket.inventory;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

public record Product(
        Long id,
        @NotBlank String name,
        @NotBlank String category,
        @NotBlank String branch,
        @Min(0) int stock,
        @Min(0) int minStock,
        BigDecimal price,
        int soldUnits) {
    public boolean requiresPurchaseOrder() {
        return stock <= minStock;
    }
}
