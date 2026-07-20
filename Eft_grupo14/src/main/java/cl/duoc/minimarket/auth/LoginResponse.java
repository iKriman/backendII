package cl.duoc.minimarket.auth;

import java.util.Set;

public record LoginResponse(String token, String tokenType, String username, Set<Role> roles) {
}
