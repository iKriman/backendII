package cl.duoc.minimarket;

import cl.duoc.minimarket.auth.JwtService;
import cl.duoc.minimarket.auth.Role;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityIntegrationTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    JwtService jwtService;

    @Test
    void allowsLoginAndReturnsJwt() throws Exception {
        mvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"admin\",\"password\":\"admin123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.roles[0]").value("ADMIN"));
    }

    @Test
    void protectsAdministrativeReports() throws Exception {
        mvc.perform(get("/api/reports/sales"))
                .andExpect(status().isForbidden());

        String token = jwtService.createToken("admin", Set.of(Role.ADMIN));
        mvc.perform(get("/api/reports/sales").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ordersCount").value(0));
    }
}
