package cl.duoc.minimarket.reports;

import cl.duoc.minimarket.orders.SalesOrder;
import java.math.BigDecimal;
import java.util.List;

public record SalesSummary(int ordersCount, BigDecimal totalSales, List<SalesOrder> orders) {
}
