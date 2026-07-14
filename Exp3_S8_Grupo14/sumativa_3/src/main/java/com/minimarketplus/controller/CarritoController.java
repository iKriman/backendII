package com.minimarketplus.controller;

import com.minimarketplus.model.CarritoItem;
import com.minimarketplus.service.CarritoService;
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
@RequestMapping("/api/carrito")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping
    @Operation(summary = "Lista productos del carrito", description = "Retorna los productos agregados al carrito con enlaces de navegacion.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Carrito consultado correctamente"),
            @ApiResponse(responseCode = "401", description = "Credenciales no enviadas o invalidas")
    })
    public CollectionModel<EntityModel<CarritoItem>> listar() {
        List<EntityModel<CarritoItem>> items = carritoService.listar().stream()
                .map(this::toModel)
                .toList();
        return CollectionModel.of(items,
                linkTo(methodOn(CarritoController.class).listar()).withSelfRel(),
                linkTo(methodOn(ProductoController.class).listar()).withRel("productos"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Agrega un producto al carrito", description = "Registra un item en el carrito y devuelve enlaces HATEOAS.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto agregado al carrito"),
            @ApiResponse(responseCode = "400", description = "Datos del item invalidos"),
            @ApiResponse(responseCode = "403", description = "Usuario sin rol ADMIN")
    })
    public EntityModel<CarritoItem> agregar(@Valid @RequestBody CarritoItem item) {
        return toModel(carritoService.agregar(item));
    }

    private EntityModel<CarritoItem> toModel(CarritoItem item) {
        return EntityModel.of(item,
                linkTo(methodOn(CarritoController.class).listar()).withRel("carrito"),
                linkTo(methodOn(ProductoController.class).listar()).withRel("productos"));
    }
}
