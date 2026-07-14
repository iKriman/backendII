package com.minimarketplus.service;

import com.minimarketplus.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UsuarioService {

    private final List<Usuario> usuarios = new ArrayList<>();
    private final AtomicLong sequence = new AtomicLong(3);

    public UsuarioService() {
        usuarios.add(new Usuario(1L, "Administrador Minimarket", "admin@minimarketplus.cl", "ADMIN"));
        usuarios.add(new Usuario(2L, "Cliente Demo", "cliente@minimarketplus.cl", "CLIENTE"));
    }

    public List<Usuario> listar() {
        return List.copyOf(usuarios);
    }

    public Usuario crear(Usuario usuario) {
        Usuario nuevo = new Usuario(
                sequence.getAndIncrement(),
                usuario.nombre(),
                usuario.email(),
                usuario.rol());
        usuarios.add(nuevo);
        return nuevo;
    }
}
