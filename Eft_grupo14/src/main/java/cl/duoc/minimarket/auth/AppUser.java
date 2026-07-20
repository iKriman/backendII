package cl.duoc.minimarket.auth;

import java.util.Set;

public record AppUser(String username, String encodedPassword, Set<Role> roles) {
}
