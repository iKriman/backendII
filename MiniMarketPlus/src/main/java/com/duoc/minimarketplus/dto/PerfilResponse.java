package com.duoc.minimarketplus.dto;

import java.util.List;

public record PerfilResponse(
        String username,
        List<String> roles,
        String modeloAutenticacion,
        String negocio
) {
}
