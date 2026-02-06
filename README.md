# API de Gestión de Franquicias
API REST  desarrollada en Spring Boot para la gestión de franquicias, sucursales y productos, cumpliendo los criterios funcionales y técnicos solicitados.

La solución fue diseñada con un enfoque limpio, escalable y profesional, priorizando buenas prácticas de arquitectura, control de versiones e infraestructura como código.

---

Tecnologías Utilizadas

### Backend
* Java 21
* Spring Boot 4.0.2
* Spring Web
* Spring Data MongoDB
* Bean Validation
* Lombok

### Persistencia
* MongoDB Atlas (Cloud)
* Modelo de documentos con agregados embebidos.

### Infraestructura
* Terraform
* MongoDB Atlas Provider

### Control de Versiones
* Git
* Utilización de ramas feature, development y main.

---

## Descripción general de la solución

La API permite administrar una franquicia como entidad principal, asociando a esta múltiples sucursales, y a cada sucursal múltiples productos con control de stock.

Las operaciones de actualización y eliminación se realizan utilizando **identificadores únicos (ID)** para garantizar unicidad, consistencia y evitar ambigüedad en los datos.

La lógica de negocio se encuentra desacoplada de la capa de presentación y de la tecnología de persistencia.

---
## Modelo de datos

Una **Franquicia** contiene una lista de **Sucursales**, y cada sucursal contiene una lista de **Productos** con su stock.

Ejemplo de documento almacenado en MongoDB:

```json
{
  "id": "ObjectId",
  "name": "Franquicia Ejemplo",
  "branches": [
    {
      "id": "ObjectId",
      "name": "Sucursal Centro",
      "products": [
        {
          "id": "ObjectId",
          "name": "Producto A",
          "stock": 100
        }
      ]
    }
  ]
}
```
---
## Endpoints disponibles
### Franquicias

- `POST /api/franchises`: Crear una nueva franquicia.
- `PUT /api/franchises/{idFranchise}/name`: Actualiza el nombre de una franquicia existente.

### Surcursales
- `POST /api/franchises/{idFranchise}/branches`: Agregar una nueva sucursal a una franquicia existente.
- `PUT /api/franchises/{idFranchise}/branches/{idBranch}/name`: Actualiza el nombre de una sucursal existente.

### Productos
- `POST /api/franchises/{idFranchise}/branches/{idBranch}/products`: Agregar un nuevo producto a una sucursal existente.
- `PUT /api/franchises/{idFranchise}/branches/{idBranch}/products/{idProduct}/stock`: Actualiza el stock de un producto existente.
- `DELETE /api/franchises/{idFranchise}/branches/{idBranch}/products/{idProduct}`: Elimina un producto de una sucursal existente.
- `GET /api/franchises/{idFranchise}/products-max-stock`: Obtiene el producto con mayor stock de una franquicia por sucursales.
---
## Manejo de errores
La API implementa un manejo de errores robusto, devolviendo códigos HTTP adecuados y mensajes de error claros para facilitar la depuración y el uso correcto de la API.

Ejemplo:

```json
{
  "timestamp": "2024-06-01T12:00:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Franchise not found",
  "path": "/api/franchises/{idFranchise}/branches"
}
```

Se manejan, entre otros:
- Errores de validación (400 Bad Request)
- Recursos no encontrados (404 Not Found)
- Errores de servidor (500 Internal Server Error)
---
## Infraestructura como código
La persistencia se aprovisiona utilizando Terraform, creando automáticamente los recursos necesarios en MongoDB Atlas, incluyendo:

- Proyecto
- Cluster
- Usuario
- Configuración de Acceso por IP

*El acceso se encuentra abierto para la prueba técnica.*

---
## Configuración de la aplicación
MongoDB Atlas provee el host, por ejemplo:
```
mongodb+srv://<username>:<password>@cluster0.mongodb.net/myFirstDatabase?retryWrites=true&w=majority
```

El string de conexión se configura en el archivo `application.properties` de Spring Boot:

```properties
spring.data.mongodb.uri=mongodb+srv://<username>:<password>@cluster0.mongodb.net/myFirstDatabase?retryWrites=true&w=majority
```
---
## Ejecución local
Requisitos
* Java 21
* Maven
* Acceso a MongoDB Atlas
* Terraform (Opcional, para aprovisionar infraestructura)

Ejecutar la aplicación
```
mvn clean spring-boot:run
```

La API estará disponible en `http://localhost:8080/`.

---

## Ejecución con Docker

La aplicación puede ejecutarse dentro de un contenedor Docker utilizando **Java 21**, garantizando consistencia entre el entorno de compilación y el entorno de ejecución.

### Requisitos
- Docker
- Docker Compose

### Configuración

La aplicación utiliza la variable de entorno `MONGODB_URI` para conectarse a MongoDB Atlas.

Ejemplo:

```bash
export MONGODB_URI="mongodb+srv://USUARIO:CONTRASEÑA@cluster-franquicias.ta0nizu.mongodb.net/franquicias_db?retryWrites=true&w=majority"
```

### Construir y ejecutar

```bash
docker compose up --build
```
La API estará disponible en `http://localhost:8080/`.

---
## Flujo de trabajo
El desarrollo se realizó utilizando Git, siguiendo un flujo de trabajo basado en ramas:
- `main`: Rama principal, contiene el código de producción.
- `development`: Rama de integración, donde se fusionan las ramas de características.
- `feature/{feature-name}`: Ramas de características para cada funcionalidad específica.

---

## Autor
Harold Rueda

Prueba técnica - backend (Accenture)