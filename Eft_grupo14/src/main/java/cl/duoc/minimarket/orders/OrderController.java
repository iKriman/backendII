package cl.duoc.minimarket.orders;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public ResponseEntity<EntityModel<SalesOrder>> create(@Valid @RequestBody OrderRequest request) {
        SalesOrder order = orderService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(order));
    }

    @GetMapping("/orders")
    public CollectionModel<EntityModel<SalesOrder>> orders() {
        List<EntityModel<SalesOrder>> models = orderService.findAll().stream().map(this::toModel).toList();
        return CollectionModel.of(models, linkTo(methodOn(OrderController.class).orders()).withSelfRel());
    }

    @GetMapping("/orders/{id}")
    public EntityModel<SalesOrder> order(@PathVariable Long id) {
        return orderService.findAll().stream()
                .filter(order -> order.id().equals(id))
                .findFirst()
                .map(this::toModel)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado: " + id));
    }

    @GetMapping("/promotions")
    public CollectionModel<Promotion> promotions() {
        return CollectionModel.of(orderService.promotions(),
                linkTo(methodOn(OrderController.class).promotions()).withSelfRel());
    }

    private EntityModel<SalesOrder> toModel(SalesOrder order) {
        return EntityModel.of(order,
                linkTo(methodOn(OrderController.class).order(order.id())).withSelfRel(),
                linkTo(methodOn(OrderController.class).orders()).withRel("orders"),
                linkTo(methodOn(OrderController.class).promotions()).withRel("promotions"));
    }
}
