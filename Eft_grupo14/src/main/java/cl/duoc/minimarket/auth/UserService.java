package cl.duoc.minimarket.auth;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final Map<String, AppUser> users;

    public UserService(PasswordEncoder passwordEncoder) {
        this.users = Map.of(
                "admin", new AppUser("admin", passwordEncoder.encode("admin123"), Set.of(Role.ADMIN)),
                "cajero", new AppUser("cajero", passwordEncoder.encode("cajero123"), Set.of(Role.CAJERO)),
                "cliente", new AppUser("cliente", passwordEncoder.encode("cliente123"), Set.of(Role.CLIENTE))
        );
    }

    public Optional<AppUser> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }
}
