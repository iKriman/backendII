package cl.duoc.minimarket.orders;

import cl.duoc.minimarket.inventory.InventoryService;
import cl.duoc.minimarket.inventory.Product;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final AtomicLong orderSequence = new AtomicLong(900);
    private final InventoryService inventoryService;
    private final List<SalesOrder> orders = new CopyOnWriteArrayList<>();
    private final List<Promotion> promotions = List.of(
            new Promotion(1L, "Semana bebidas", "Bebidas", new BigDecimal("10")),
            new Promotion(2L, "Lacteos seleccionados", "Lacteos", new BigDecimal("5"))
    );

    public OrderService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public SalesOrder create(OrderRequest request) {
        List<OrderLine> lines = new ArrayList<>();
        for (OrderItemRequest item : request.items()) {
            Product product = inventoryService.findById(item.productId());
            inventoryService.registerSale(item.productId(), item.quantity());
            BigDecimal unitPrice = applyPromotion(product);
            lines.add(new OrderLine(product.id(), product.name(), item.quantity(), unitPrice,
                    unitPrice.multiply(BigDecimal.valueOf(item.quantity()))));
        }
        BigDecimal total = lines.stream().map(OrderLine::subtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        SalesOrder order = new SalesOrder(orderSequence.incrementAndGet(), request.customerName(),
                request.deliveryType(), List.copyOf(lines), total, "CONFIRMADO", Instant.now());
        orders.add(order);
        return order;
    }

    public List<SalesOrder> findAll() {
        return List.copyOf(orders);
    }

    public List<Promotion> promotions() {
        return promotions;
    }

    private BigDecimal applyPromotion(Product product) {
        BigDecimal discount = promotions.stream()
                .filter(promotion -> promotion.category().equalsIgnoreCase(product.category()))
                .findFirst()
                .map(Promotion::discountPercent)
                .orElse(BigDecimal.ZERO);
        BigDecimal factor = BigDecimal.ONE.subtract(discount.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP));
        return product.price().multiply(factor).setScale(0, RoundingMode.HALF_UP);
    }
}
