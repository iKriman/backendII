package cl.duoc.minimarket.orders;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequest(@NotNull Long productId, @Min(1) int quantity) {
}
