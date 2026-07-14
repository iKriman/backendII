package com.minimarketplus.controller;

import com.minimarketplus.model.MovimientoInventario;
import com.minimarketplus.service.InventarioService;
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
@RequestMapping("/api/inventario")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping
    @Operation(summary = "Lista los movimientos de inventario", description = "Consulta entradas y salidas de stock con enlaces HATEOAS.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movimientos consultados correctamente"),
            @ApiResponse(responseCode = "401", description = "Credenciales no enviadas o invalidas")
    })
    public CollectionModel<EntityModel<MovimientoInventario>> listar() {
        List<EntityModel<MovimientoInventario>> movimientos = inventarioService.listar().stream()
                .map(this::toModel)
                .toList();
        return CollectionModel.of(movimientos,
                linkTo(methodOn(InventarioController.class).listar()).withSelfRel(),
                linkTo(methodOn(ProductoController.class).listar()).withRel("productos"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Registra un nuevo movimiento", description = "Crea un movimiento de stock y agrega enlaces relacionados.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Movimiento registrado"),
            @ApiResponse(responseCode = "400", description = "Datos de movimiento invalidos"),
            @ApiResponse(responseCode = "403", description = "Usuario sin rol ADMIN")
    })
    public EntityModel<MovimientoInventario> registrar(@Valid @RequestBody MovimientoInventario movimiento) {
        return toModel(inventarioService.registrar(movimiento));
    }

    private EntityModel<MovimientoInventario> toModel(MovimientoInventario movimiento) {
        return EntityModel.of(movimiento,
                linkTo(methodOn(InventarioController.class).listar()).withRel("inventario"),
                linkTo(methodOn(ProductoController.class).listar()).withRel("productos"));
    }
}
