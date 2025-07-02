# gestion-vendedor-api

## ¿Qué es esto?

Esta es una API REST para manejar vendedores del sistema Perfunlandia. Tiene documentación automática con Swagger y enlaces HATEOAS para navegar fácilmente entre recursos.

## 🚀 Cómo usar

### 1. Ejecutar la aplicación
```bash
mvn spring-boot:run
```

### 2. Ver la documentación
Abre tu navegador y ve a: **http://localhost:8086/swagger-ui.html**

Aquí podrás ver todos los endpoints disponibles y probarlos directamente.

## 📋 Endpoints disponibles

### Vendedores (endpoints normales)
- `POST /api/vendedores` - Crear un nuevo vendedor
- `GET /api/vendedores/{id}` - Ver un vendedor específico
- `PUT /api/vendedores` - Actualizar sucursal del vendedor
- `GET /api/vendedores/sucursal/{idSucursal}` - Ver vendedores por sucursal

### Vendedores con HATEOAS (apuntan al API Gateway)
- `POST /api/vendedores/hateoas` - Crear vendedor con enlaces HATEOAS
- `GET /api/vendedores/hateoas/{id}` - Ver vendedor con enlaces HATEOAS
- `PUT /api/vendedores/hateoas` - Actualizar sucursal con enlaces HATEOAS
- `GET /api/vendedores/hateoas/sucursal/{idSucursal}` - Ver vendedores por sucursal con HATEOAS

## 🔗 Enlaces HATEOAS

Cada respuesta con HATEOAS incluye enlaces que apuntan al API Gateway (puerto 8888):

```json
{
  "idVendedor": 1,
  "idUsuario": 1,
  "nombreUsuario": "Juan Pérez",
  "idSucursal": 2,
  "_links": {
    "self": "http://localhost:8888/api/vendedores/1",
    "collection": "http://localhost:8888/api/vendedores",
    "sucursal": "http://localhost:8888/api/vendedores/sucursal/2"
  }
}
```

## 🌐 URLs importantes

- **API local**: http://localhost:8086/api/vendedores
- **API Gateway**: http://localhost:8888/api/vendedores
- **Documentación**: http://localhost:8086/swagger-ui.html

## 🛠️ Tecnologías usadas

- **Spring Boot 3.4.0** - Framework principal
- **HATEOAS** - Para enlaces entre recursos (todos apuntan al Gateway)
- **Swagger/OpenAPI** - Para documentación automática
- **MariaDB** - Base de datos
- **JPA/Hibernate** - Para manejo de datos

## 📝 Notas

- La API corre en el puerto 8086
- **Todos los enlaces HATEOAS apuntan al API Gateway en puerto 8888**
- Toda la documentación se genera automáticamente
- Un solo controlador maneja todas las operaciones de vendedores
- No necesitas configurar nada más, solo ejecutar la aplicación

## 🎯 Ejemplo rápido

1. Ejecuta la aplicación
2. Ve a http://localhost:8086/swagger-ui.html
3. Prueba el endpoint "Crear vendedor con HATEOAS"
4. Verás la respuesta con enlaces HATEOAS que van al Gateway
5. Usa los enlaces para navegar entre recursos

¡Eso es todo! La API está lista para usar. 🎉
