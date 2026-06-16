package com.minimarket;

import com.minimarket.entity.Venta;
import com.minimarket.entity.Usuario;
import com.minimarket.entity.DetalleVenta;
import com.minimarket.entity.Producto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VentaTest {

    @Mock
    private List<Venta> ventaRepositoryMock;

    @Test
    void testValidacionStockSuficiente() {
        Producto p = new Producto();
        p.setStock(10);

        int cantidadSolicitada = 5;
        assertTrue(cantidadSolicitada <= p.getStock(), "Debe permitir venta si hay stock suficiente");
    }

    @Test
    void testCalculoTotalVenta() {
        DetalleVenta d1 = new DetalleVenta();
        d1.setPrecio(100.0);
        d1.setCantidad(2);

        DetalleVenta d2 = new DetalleVenta();
        d2.setPrecio(50.0);
        d2.setCantidad(1);

        List<DetalleVenta> detalles = List.of(d1, d2);
        double total = detalles.stream().mapToDouble(d -> d.getPrecio() * d.getCantidad()).sum();

        assertEquals(250.0, total);
    }

    @Test
    void testRelacionUsuarioYVenta() {
        Usuario u = new Usuario();
        Venta v = new Venta();
        v.setUsuario(u);
        assertNotNull(v.getUsuario());
    }

    @Test
    void testSimulacionGuardado() {
        Venta v = new Venta();
        when(ventaRepositoryMock.add(v)).thenReturn(true);
        assertTrue(ventaRepositoryMock.add(v));
    }

    @Test
    void testGettersSetters() {
        Venta v = new Venta();
        v.setId(1L);
        v.setFecha(new Date());
        assertEquals(1L, v.getId());
        assertNotNull(v.getFecha());
    }
}