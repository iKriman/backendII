package com.minimarketplus.service;

import com.minimarketplus.model.MovimientoInventario;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InventarioService {

    private final List<MovimientoInventario> movimientos = new ArrayList<>();
    private final AtomicLong sequence = new AtomicLong(2);

    public InventarioService() {
        movimientos.add(new MovimientoInventario(1L, 1L, "ENTRADA", 20, "Recepcion inicial", LocalDateTime.now()));
    }

    public List<MovimientoInventario> listar() {
        return List.copyOf(movimientos);
    }

    public MovimientoInventario registrar(MovimientoInventario movimiento) {
        MovimientoInventario nuevo = new MovimientoInventario(
                sequence.getAndIncrement(),
                movimiento.productoId(),
                movimiento.tipo(),
                movimiento.cantidad(),
                movimiento.descripcion(),
                LocalDateTime.now());
        movimientos.add(nuevo);
        return nuevo;
    }
}
