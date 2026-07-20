package cl.duoc.minimarket.inventory;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/products")
    public CollectionModel<EntityModel<Product>> products() {
        List<EntityModel<Product>> items = inventoryService.findAll().stream().map(this::toModel).toList();
        return CollectionModel.of(items, linkTo(methodOn(InventoryController.class).products()).withSelfRel());
    }

    @GetMapping("/products/{id}")
    public EntityModel<Product> product(@PathVariable Long id) {
        return toModel(inventoryService.findById(id));
    }

    @PostMapping("/inventory/products")
    public ResponseEntity<EntityModel<Product>> create(@Valid @RequestBody ProductRequest request) {
        Product product = inventoryService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(product));
    }

    @PatchMapping("/inventory/products/{id}/stock")
    public EntityModel<Product> updateStock(@PathVariable Long id, @Valid @RequestBody StockUpdateRequest request) {
        return toModel(inventoryService.updateStock(id, request.stock()));
    }

    @GetMapping("/inventory/purchase-orders")
    public CollectionModel<PurchaseOrder> purchaseOrders() {
        return CollectionModel.of(inventoryService.purchaseOrders(),
                linkTo(methodOn(InventoryController.class).purchaseOrders()).withSelfRel());
    }

    private EntityModel<Product> toModel(Product product) {
        EntityModel<Product> model = EntityModel.of(product,
                linkTo(methodOn(InventoryController.class).product(product.id())).withSelfRel(),
                linkTo(methodOn(InventoryController.class).products()).withRel("products"));
        if (product.requiresPurchaseOrder()) {
            model.add(linkTo(methodOn(InventoryController.class).purchaseOrders()).withRel("purchase-orders"));
        }
        return model;
    }
}
