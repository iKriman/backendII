package cl.duoc.minimarket.orders;

import java.math.BigDecimal;

public record OrderLine(Long productId, String productName, int quantity, BigDecimal unitPrice, BigDecimal subtotal) {
}
