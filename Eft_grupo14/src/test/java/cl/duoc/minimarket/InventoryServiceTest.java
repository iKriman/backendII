package cl.duoc.minimarket;

import cl.duoc.minimarket.inventory.InventoryService;
import cl.duoc.minimarket.inventory.ProductRequest;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InventoryServiceTest {
    @Test
    void createsPurchaseOrderWhenStockIsUnderMinimum() {
        InventoryService service = new InventoryService();

        var product = service.create(new ProductRequest("Cafe instantaneo", "Abarrotes",
                "Nunoa", 3, 10, new BigDecimal("4990")));

        assertThat(service.purchaseOrders())
                .anyMatch(order -> order.productId().equals(product.id()) && order.suggestedUnits() > 0);
    }

    @Test
    void rejectsSaleWhenStockIsInsufficient() {
        InventoryService service = new InventoryService();
        var product = service.create(new ProductRequest("Helado familiar", "Congelados",
                "La Florida", 2, 5, new BigDecimal("5990")));

        assertThatThrownBy(() -> service.registerSale(product.id(), 3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Stock insuficiente");
    }
}
