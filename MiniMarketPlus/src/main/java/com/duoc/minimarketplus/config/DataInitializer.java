package com.duoc.minimarketplus.config;

import com.duoc.minimarketplus.entity.Usuario;
import com.duoc.minimarketplus.entity.Rol;
import com.duoc.minimarketplus.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setUsername("ignacio");
            admin.setNombreCompleto("Ignacio Kriman");
            admin.setPassword(passwordEncoder.encode("123456"));

            admin.setRoles(Set.of(Rol.ADMIN));

            usuarioRepository.save(admin);
            System.out.println(">>> Usuario de prueba creado con éxito: ignacio / 123456");
        }
    }
}