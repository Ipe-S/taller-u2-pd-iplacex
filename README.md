# Deccos — Tienda Online

Aplicación web de tienda de decoración e iluminación desarrollada como taller de patrones de diseño (Unidad 2). Implementa tres patrones GoF sobre un stack Spring Boot 3.4.2 / Java 17.

## Stack tecnológico

| Componente | Tecnología |
|---|---|
| Backend | Spring Boot 3.4.2 / Java 17 |
| Persistencia | Spring Data JPA + H2 (in-memory) |
| Plantillas | Thymeleaf + Thymeleaf Extras Spring Security 6 |
| Seguridad | Spring Security 6 |
| Build | Gradle 8 |

## Arquitectura de módulos

```
cl.patrones.taller.u2
├── bodegaje/          Productos, bodegas y stock
│   ├── domain/        Producto, Stock, Bodega
│   ├── repository/    ProductoRepository
│   └── service/       BodegajeService / BodegajeServiceImpl
│
├── catalogo/          Categorías y presentación en UI
│   ├── domain/        Aviso, Categoria, Clasificacion
│   │                  ProductoAvisoAdapter  ← patrón Adapter
│   ├── repository/    CategoriaRepository, ClasificacionRepository
│   └── service/       CategoriaService / CategoriaServiceImpl
│
├── clientes/          Cuentas de cliente
│   ├── Cliente.java
│   ├── ClienteAnonimo.java   ← patrón Null Object
│   ├── ClienteRepository
│   └── ClienteService / ClienteServiceImpl
│
├── pedidos/           Carrito de sesión
│   ├── Carrito.java          (@SessionScope)
│   └── ItemCarrito.java
│
├── tienda/            Capa web transversal
│   ├── adapter/
│   │   ├── Usuario.java      (UserDetails wrapper)
│   │   └── UsuarioAnonimo.java  ← patrón Null Object
│   ├── controller/
│   │   ├── TiendaController.java
│   │   └── MenuControllerAdvice.java
│   ├── menu/
│   │   ├── ItemMenu.java         ← interface Composite
│   │   ├── EnlaceItemMenu.java   ← Leaf
│   │   ├── CategoriaMenu.java    ← Composite
│   │   └── util/Slugger.java
│   └── service/AuthService.java
│
└── SecurityConfig.java
```
---

## Ejecutar el proyecto

**Requisitos:** Java 17+, Gradle (o usar el wrapper incluido).

```bash
# Windows
gradlew.bat bootRun

# Linux / macOS
./gradlew bootRun
```
---

## Credenciales de prueba

| Usuario | Contraseña |
|---|---|
| jperez@123.cl | 1234 |
| asoto@123.cl | 4321 |

Los visitantes sin sesión son atendidos por `UsuarioAnonimo` (nombre: *Invitado*).

---

## Consola H2

Disponible en `http://localhost:8080/h2-console` mientras la aplicación está en ejecución.

| Campo | Valor |
|---|---|
| JDBC URL | `jdbc:h2:mem:tiendadb` |
| User | `sa` |
| Password | *(vacío)* |

---

## Catálogo de productos de ejemplo

| SKU | Nombre | Categoría |
|---|---|---|
| lamp-001 | Lámpara LumiGlow | Comedor |
| lamp-002 | Lámpara NordicBeam | Comedor |
| lamp-003 | Lámpara Nomad Light | Comedor |
| lamp-004 | Lámpara HaloTouch | Dormitorio |
| sill-001 | Silla Lounge | Modernas |
| sill-002 | Silla Clásica Moderna | Clásicas |

---

## Estructura de plantillas

```
src/main/resources/templates/
├── layout.html          Layout base compartido
├── inicio.html          Catálogo principal
├── categoria.html       Productos por categoría
├── carrito.html         Carrito de compras
├── login.html           Formulario de ingreso
├── ubicacion.html
├── contacto.html
└── includes/
    ├── nav.html          Navbar (menú Composite + usuario Null Object)
    ├── footer.html
    ├── carrusel.html
    └── avisos.html
```

---

## Dependencias principales

```groovy
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
implementation 'org.springframework.boot:spring-boot-starter-security'
implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
implementation 'org.springframework.boot:spring-boot-starter-web'
implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity6'
runtimeOnly    'com.h2database:h2'
```