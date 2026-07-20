package cl.duoc.minimarket.orders;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record SalesOrder(Long id, String customerName, String deliveryType, List<OrderLine> items,
                         BigDecimal total, String status, Instant createdAt) {
}
