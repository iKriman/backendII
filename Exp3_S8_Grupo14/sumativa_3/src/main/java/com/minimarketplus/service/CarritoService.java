package com.minimarketplus.service;

import com.minimarketplus.model.CarritoItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CarritoService {

    private final List<CarritoItem> items = new ArrayList<>();
    private final AtomicLong sequence = new AtomicLong(2);

    public CarritoService() {
        items.add(new CarritoItem(1L, 1L, "Arroz grado 1", 2));
    }

    public List<CarritoItem> listar() {
        return List.copyOf(items);
    }

    public CarritoItem agregar(CarritoItem item) {
        CarritoItem nuevo = new CarritoItem(
                sequence.getAndIncrement(),
                item.productoId(),
                item.nombreProducto(),
                item.cantidad());
        items.add(nuevo);
        return nuevo;
    }
}
