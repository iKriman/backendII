package com.duoc.minimarketplus.dto;

import com.duoc.minimarketplus.entity.Rol;

import java.util.Set;

public record UsuarioResponse(
        Long id,
        String username,
        String nombreCompleto,
        Set<Rol> roles
) {
}
