# MiniMarket Plus EFT - Backend II

Solucion para la Evaluacion Final Transversal de PBY2202. El proyecto implementa un backend Spring Boot para MiniMarket Plus con dominios separados tipo microservicio:

- `auth`: autenticacion segura con JWT y usuarios por rol.
- `inventory`: productos, disponibilidad por sucursal, control de stock y ordenes de compra automaticas.
- `orders`: pedidos en linea, retiro/despacho y promociones centralizadas.
- `reports`: reportes de ventas y rotacion de productos.

## Credenciales de prueba

| Usuario | Clave | Rol |
| --- | --- | --- |
| `admin` | `admin123` | ADMIN |
| `cajero` | `cajero123` | CAJERO |
| `cliente` | `cliente123` | CLIENTE |

## Ejecutar

```bash
mvn spring-boot:run
```

La API queda disponible en `http://localhost:8080`.

## Ejecutar pruebas unitarias

```bash
mvn test
```

Las pruebas validan JWT, reglas de inventario, creacion de pedidos, descuento de stock y proteccion de endpoints administrativos.

## Documentacion OpenAPI y HATEOAS

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- JSON OpenAPI generado: `http://localhost:8080/v3/api-docs`
- Especificacion entregable: `docs/openapi.yaml`

Las respuestas principales incluyen `_links` para navegacion HATEOAS, por ejemplo productos, pedidos y reportes.

## Flujo de demostracion

1. Autenticarse como `admin`:

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

2. Copiar el token y consultar reportes:

```bash
curl http://localhost:8080/api/reports/rotation \
  -H "Authorization: Bearer TOKEN"
```

3. Consultar productos disponibles:

```bash
curl http://localhost:8080/api/products
```

4. Crear pedido como cliente o cajero:

```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Authorization: Bearer TOKEN_CLIENTE" \
  -H "Content-Type: application/json" \
  -d '{"customerName":"Verioska Ramirez","deliveryType":"retiro","items":[{"productId":102,"quantity":2}]}'
```

5. Actualizar stock como administrador:

```bash
curl -X PATCH http://localhost:8080/api/inventory/products/104/stock \
  -H "Authorization: Bearer TOKEN_ADMIN" \
  -H "Content-Type: application/json" \
  -d '{"stock":5}'
```

## Buenas practicas aplicadas

- Separacion por paquetes de dominio.
- DTOs con validacion Jakarta Validation.
- Manejo centralizado de errores.
- Seguridad stateless con JWT y autorizacion por roles.
- Pruebas unitarias e integracion con MockMvc.
- Documentacion OAS y enlaces HATEOAS.
