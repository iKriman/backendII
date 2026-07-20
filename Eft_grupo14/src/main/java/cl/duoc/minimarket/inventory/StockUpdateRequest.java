package cl.duoc.minimarket.inventory;

import jakarta.validation.constraints.Min;

public record StockUpdateRequest(@Min(0) int stock) {
}
