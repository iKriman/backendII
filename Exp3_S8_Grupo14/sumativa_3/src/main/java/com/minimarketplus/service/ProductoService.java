package com.minimarketplus.service;

import com.minimarketplus.model.Producto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductoService {

    private final List<Producto> productos = new ArrayList<>();
    private final AtomicLong sequence = new AtomicLong(3);

    public ProductoService() {
        productos.add(new Producto(1L, "Arroz grado 1", "Abarrotes", BigDecimal.valueOf(1890), 35));
        productos.add(new Producto(2L, "Leche entera 1L", "Lacteos", BigDecimal.valueOf(1250), 48));
    }

    public List<Producto> listar() {
        return List.copyOf(productos);
    }

    public Producto crear(Producto producto) {
        Producto nuevo = new Producto(
                sequence.getAndIncrement(),
                producto.nombre(),
                producto.categoria(),
                producto.precio(),
                producto.stock());
        productos.add(nuevo);
        return nuevo;
    }
}
