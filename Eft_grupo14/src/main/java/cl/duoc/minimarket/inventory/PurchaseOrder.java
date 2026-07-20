package cl.duoc.minimarket.inventory;

import java.time.Instant;

public record PurchaseOrder(Long id, Long productId, String productName, String branch, int suggestedUnits, Instant createdAt) {
}
