package com.minimarket.service;

import com.minimarket.entity.Venta;
import com.minimarket.repository.VentaRepository;
import com.minimarket.service.impl.VentaServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class VentaServiceTest {

    @Autowired
    private VentaServiceImpl ventaService;

    @MockBean
    private VentaRepository ventaRepository;

    @Test
    @DisplayName("Escenario Exito: Registrar una venta de forma correcta")
    void testRegistrarVenta_Exito() {
        Venta nuevaVenta = new Venta();
        nuevaVenta.setId(1L);
        nuevaVenta.setFecha(new Date());

        when(ventaRepository.save(any(Venta.class))).thenReturn(nuevaVenta);

        Venta resultado = ventaService.save(nuevaVenta);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(ventaRepository, times(1)).save(any(Venta.class));
    }

    @Test
    @DisplayName("Escenario Error: Error de persistencia al registrar una venta")
    void testRegistrarVenta_ErrorPersistencia() {
        Venta nuevaVenta = new Venta();
        nuevaVenta.setId(1L);

        when(ventaRepository.save(any(Venta.class))).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> {
            ventaService.save(nuevaVenta);
        });
    }

    @Test
    @DisplayName("Caso Limite: Buscar una venta que no existe en el sistema")
    void testBuscarVenta_NoExiste() {
        when(ventaRepository.findById(99L)).thenReturn(Optional.empty());

        Venta resultado = ventaService.findById(99L);

        assertNull(resultado);
    }
}