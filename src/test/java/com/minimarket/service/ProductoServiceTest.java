package com.minimarket.service;

import com.minimarket.entity.Producto;
import com.minimarket.repository.ProductoRepository;
import com.minimarket.service.impl.ProductoServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductoServiceTest {

    @Autowired
    private ProductoServiceImpl productoService;

    @MockBean
    private ProductoRepository productoRepository;

    @Test
    @DisplayName("Escenario Exito: Modificar un producto correctamente")
    void testModificarProducto_Exito() {
        Producto producto = new Producto();
        producto.setId(1L);

        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto resultado = productoService.save(producto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    @DisplayName("Escenario Error: Error al intentar persistir un producto modificado")
    void testModificarProducto_Error() {
        Producto producto = new Producto();
        producto.setId(1L);

        when(productoRepository.save(any(Producto.class))).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> {
            productoService.save(producto);
        });
    }

    @Test
    @DisplayName("Caso Limite: Buscar un producto que no existe")
    void testBuscarProducto_NoExiste() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        Producto resultado = productoService.findById(99L);

        assertNull(resultado);
    }
}