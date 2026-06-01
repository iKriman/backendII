package com.duoc.minimarketplus.dto;

import java.util.List;

public record AuthResponse(
        String token,
        String type,
        long expiresIn,
        String username,
        List<String> roles
) {
}
