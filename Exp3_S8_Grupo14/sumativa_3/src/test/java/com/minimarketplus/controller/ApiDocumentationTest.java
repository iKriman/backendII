package com.minimarketplus.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApiDocumentationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void openApiJsonIsPublic() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Minimarket Plus API")));
    }

    @Test
    @WithMockUser(roles = "CLIENTE")
    void productosIncludesHateoasLinks() throws Exception {
        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._embedded.productoList[0]._links.productos.href").exists());
    }

    @Test
    @WithMockUser(roles = "CLIENTE")
    void clienteCannotCreateProducts() throws Exception {
        mockMvc.perform(post("/api/productos")
                        .contentType("application/json")
                        .content("""
                                {
                                  "nombre": "Cafe molido",
                                  "categoria": "Abarrotes",
                                  "precio": 3990,
                                  "stock": 10
                                }
                                """))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminCanCreateProducts() throws Exception {
        mockMvc.perform(post("/api/productos")
                        .contentType("application/json")
                        .content("""
                                {
                                  "nombre": "Cafe molido",
                                  "categoria": "Abarrotes",
                                  "precio": 3990,
                                  "stock": 10
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$._links.productos.href").exists());
    }
}
