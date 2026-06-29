package com.minimarket.service;

import com.minimarket.entity.Inventario;
import com.minimarket.repository.InventarioRepository;
import com.minimarket.service.impl.InventarioServiceImpl;
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
public class InventarioServiceTest {

    @Autowired
    private InventarioServiceImpl inventarioService;

    @MockBean
    private InventarioRepository inventarioRepository;

    @Test
    @DisplayName("Escenario Exito: Guardar registro de inventario correctamente")
    void testGuardarInventario_Exito() {
        Inventario inventario = new Inventario();
        inventario.setId(1L);

        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventario);

        Inventario resultado = inventarioService.save(inventario);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(inventarioRepository, times(1)).save(any(Inventario.class));
    }

    @Test
    @DisplayName("Escenario Error: Fallo en la base de datos al guardar inventario")
    void testGuardarInventario_ErrorBaseDatos() {
        Inventario inventario = new Inventario();
        inventario.setId(1L);

        when(inventarioRepository.save(any(Inventario.class))).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> {
            inventarioService.save(inventario);
        });
    }

    @Test
    @DisplayName("Caso Limite: Buscar un registro de inventario inexistente")
    void testBuscarInventario_NoExiste() {
        when(inventarioRepository.findById(99L)).thenReturn(Optional.empty());

        Inventario resultado = inventarioService.findById(99L);

        assertNull(resultado);
    }
}