package cl.duoc.minimarket.reports;

import cl.duoc.minimarket.inventory.InventoryService;
import cl.duoc.minimarket.inventory.Product;
import cl.duoc.minimarket.orders.OrderService;
import cl.duoc.minimarket.orders.SalesOrder;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final InventoryService inventoryService;
    private final OrderService orderService;

    public ReportController(InventoryService inventoryService, OrderService orderService) {
        this.inventoryService = inventoryService;
        this.orderService = orderService;
    }

    @GetMapping("/rotation")
    public CollectionModel<Product> rotation() {
        return CollectionModel.of(inventoryService.rotationReport(),
                linkTo(methodOn(ReportController.class).rotation()).withSelfRel());
    }

    @GetMapping("/sales")
    public EntityModel<SalesSummary> sales() {
        List<SalesOrder> orders = orderService.findAll();
        BigDecimal total = orders.stream().map(SalesOrder::total).reduce(BigDecimal.ZERO, BigDecimal::add);
        SalesSummary summary = new SalesSummary(orders.size(), total, orders);
        return EntityModel.of(summary,
                linkTo(methodOn(ReportController.class).sales()).withSelfRel(),
                linkTo(methodOn(ReportController.class).rotation()).withRel("rotation"));
    }
}
