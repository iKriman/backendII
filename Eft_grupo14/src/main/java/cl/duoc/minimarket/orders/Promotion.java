package cl.duoc.minimarket.orders;

import java.math.BigDecimal;

public record Promotion(Long id, String name, String category, BigDecimal discountPercent) {
}
