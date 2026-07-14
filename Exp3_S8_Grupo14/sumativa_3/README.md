# Sumativa 3 - Minimarket Plus

Proyecto Spring Boot para la evaluacion de Semana 8: documentacion avanzada con OpenAPI, Swagger UI y HATEOAS.

## Tecnologias

- Java 17
- Spring Boot 3.4.1
- Maven
- Spring Web
- Spring Security
- Spring HATEOAS
- springdoc-openapi
- JUnit 5 y MockMvc

## Ejecucion

```bash
mvn spring-boot:run
```

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```

## Usuarios de prueba

| Usuario | Password | Rol |
| --- | --- | --- |
| admin | admin123 | ADMIN |
| cliente | cliente123 | CLIENTE |

Los endpoints `GET /api/**` permiten roles `ADMIN` y `CLIENTE`. Los endpoints `POST /api/**` requieren rol `ADMIN`.

## Endpoints principales

- `GET /api/productos`
- `POST /api/productos`
- `GET /api/carrito`
- `POST /api/carrito`
- `GET /api/inventario`
- `POST /api/inventario`
- `GET /api/usuarios`
- `POST /api/usuarios`

Cada respuesta incluye enlaces HATEOAS bajo la propiedad `_links`.

## Pruebas

```bash
mvn test
```
