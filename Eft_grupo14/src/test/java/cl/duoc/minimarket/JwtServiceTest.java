package cl.duoc.minimarket;

import cl.duoc.minimarket.auth.JwtService;
import cl.duoc.minimarket.auth.Role;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceTest {
    @Test
    void createsTokenWithSubjectAndRoles() {
        JwtService service = new JwtService("MiniMarketPlusEftSecretKey2026ForJwtSigningAndValidation", 30);

        String token = service.createToken("admin", Set.of(Role.ADMIN));

        assertThat(service.parse(token).getSubject()).isEqualTo("admin");
        assertThat(service.roles(token)).contains("ADMIN");
    }
}
