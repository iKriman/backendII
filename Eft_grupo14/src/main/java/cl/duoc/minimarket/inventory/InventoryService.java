package cl.duoc.minimarket.inventory;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private final AtomicLong productSequence = new AtomicLong(100);
    private final AtomicLong purchaseOrderSequence = new AtomicLong(500);
    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final List<PurchaseOrder> purchaseOrders = new ArrayList<>();

    public InventoryService() {
        create(new ProductRequest("Arroz grado 1", "Abarrotes", "Santiago Centro", 70, 20, new BigDecimal("1590")));
        create(new ProductRequest("Bebida Coca-Cola 1.5L", "Bebidas", "Providencia", 45, 15, new BigDecimal("2190")));
        create(new ProductRequest("Leche Soprole 1L", "Lacteos", "Las Condes", 18, 10, new BigDecimal("1290")));
        create(new ProductRequest("Detergente Unilever", "Limpieza", "Maipu", 8, 12, new BigDecimal("3990")));
    }

    public List<Product> findAll() {
        return products.values().stream().sorted(Comparator.comparing(Product::id)).toList();
    }

    public Product findById(Long id) {
        Product product = products.get(id);
        if (product == null) {
            throw new IllegalArgumentException("Producto no encontrado: " + id);
        }
        return product;
    }

    public Product create(ProductRequest request) {
        Long id = productSequence.incrementAndGet();
        Product product = new Product(id, request.name(), request.category(), request.branch(),
                request.stock(), request.minStock(), request.price(), 0);
        products.put(id, product);
        evaluateReplenishment(product);
        return product;
    }

    public Product updateStock(Long id, int stock) {
        Product current = findById(id);
        Product updated = new Product(current.id(), current.name(), current.category(), current.branch(),
                stock, current.minStock(), current.price(), current.soldUnits());
        products.put(id, updated);
        evaluateReplenishment(updated);
        return updated;
    }

    public void registerSale(Long productId, int quantity) {
        Product current = findById(productId);
        if (current.stock() < quantity) {
            throw new IllegalArgumentException("Stock insuficiente para " + current.name());
        }
        Product updated = new Product(current.id(), current.name(), current.category(), current.branch(),
                current.stock() - quantity, current.minStock(), current.price(), current.soldUnits() + quantity);
        products.put(productId, updated);
        evaluateReplenishment(updated);
    }

    public List<PurchaseOrder> purchaseOrders() {
        return List.copyOf(purchaseOrders);
    }

    public List<Product> rotationReport() {
        return products.values().stream()
                .sorted(Comparator.comparing(Product::soldUnits).reversed())
                .toList();
    }

    private void evaluateReplenishment(Product product) {
        if (product.requiresPurchaseOrder()) {
            int suggestedUnits = Math.max(product.minStock() * 3 - product.stock(), product.minStock());
            purchaseOrders.add(new PurchaseOrder(purchaseOrderSequence.incrementAndGet(), product.id(),
                    product.name(), product.branch(), suggestedUnits, Instant.now()));
        }
    }
}
