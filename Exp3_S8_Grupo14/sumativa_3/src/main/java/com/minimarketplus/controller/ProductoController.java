package com.minimarketplus.controller;

import com.minimarketplus.model.Producto;
import com.minimarketplus.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    @Operation(summary = "Lista todos los productos", description = "Retorna el catalogo de productos con enlaces HATEOAS.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Productos encontrados"),
            @ApiResponse(responseCode = "401", description = "Credenciales no enviadas o invalidas")
    })
    public CollectionModel<EntityModel<Producto>> listar() {
        List<EntityModel<Producto>> productos = productoService.listar().stream()
                .map(this::toModel)
                .toList();
        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoController.class).listar()).withSelfRel(),
                linkTo(methodOn(CarritoController.class).listar()).withRel("carrito"),
                linkTo(methodOn(InventarioController.class).listar()).withRel("inventario"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Agrega un producto nuevo", description = "Crea un producto y devuelve enlaces para navegar el API.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto creado"),
            @ApiResponse(responseCode = "400", description = "Datos de producto invalidos"),
            @ApiResponse(responseCode = "403", description = "Usuario sin rol ADMIN")
    })
    public EntityModel<Producto> crear(@Valid @RequestBody Producto producto) {
        return toModel(productoService.crear(producto));
    }

    private EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
                linkTo(methodOn(ProductoController.class).listar()).withRel("productos"),
                linkTo(methodOn(CarritoController.class).listar()).withRel("carrito"));
    }
}
