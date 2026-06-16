package com.minimarket;

import com.minimarket.entity.Rol;
import com.minimarket.entity.Usuario;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    @Test
    public void testValidarDatosCompletos() {
        Usuario u = new Usuario();
        u.setNombre("Juan");
        u.setApellido("Perez");
        u.setEmail("juan@mail.com");
        u.setDireccion("Calle 123");

        assertNotNull(u.getNombre());
        assertNotNull(u.getApellido());
        assertNotNull(u.getEmail());
        assertNotNull(u.getDireccion());
    }

    @Test
    public void testAccesoSegunRol() {
        Usuario u = new Usuario();
        u.setRoles(Set.of(new Rol("ADMIN")));

        boolean puedeRegistrarVenta = u.getRoles().stream()
                .anyMatch(r -> r.getNombre().equals("ADMIN"));

        assertTrue(puedeRegistrarVenta);
    }

    @Test
    public void testEqualsAndSetters() {
        Usuario u1 = new Usuario();
        u1.setId(1L);
        u1.setUsername("user");

        Usuario u2 = new Usuario();
        u2.setId(1L);
        u2.setUsername("user");

        assertEquals(u1.getId(), u2.getId());
        assertEquals("user", u1.getUsername());
    }
}