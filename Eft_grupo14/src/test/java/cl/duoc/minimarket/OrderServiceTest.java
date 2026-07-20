package cl.duoc.minimarket;

import cl.duoc.minimarket.inventory.InventoryService;
import cl.duoc.minimarket.inventory.ProductRequest;
import cl.duoc.minimarket.orders.OrderItemRequest;
import cl.duoc.minimarket.orders.OrderRequest;
import cl.duoc.minimarket.orders.OrderService;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderServiceTest {
    @Test
    void createsConfirmedOrderAndDiscountsStock() {
        InventoryService inventory = new InventoryService();
        var product = inventory.create(new ProductRequest("Jugo naranja 1L", "Bebidas",
                "Santiago Centro", 10, 3, new BigDecimal("2000")));
        OrderService orders = new OrderService(inventory);

        var order = orders.create(new OrderRequest("Cliente Demo", "retiro",
                List.of(new OrderItemRequest(product.id(), 2))));

        assertThat(order.status()).isEqualTo("CONFIRMADO");
        assertThat(order.total()).isEqualByComparingTo("3600");
        assertThat(inventory.findById(product.id()).stock()).isEqualTo(8);
    }
}
