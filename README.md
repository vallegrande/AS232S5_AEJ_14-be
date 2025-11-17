# AS232S5 - Jheferson Jossue Torres Humareda

## 🚀 Proyecto Spring Boot WebFlux - Chatbot & Age Detection API

API REST reactiva desarrollada con Spring Boot 3.5.5 y WebFlux, que implementa funcionalidades de chatbot y detección de edad.

## 🐳 Docker Compose

Este proyecto incluye configuración completa de Docker Compose con soporte para variables de entorno.

### Inicio Rápido

```bash
# Levantar el servicio
docker-compose up -d

# Probar endpoint GET público
curl http://localhost:8085/api/chat-messages
```

### Testeo con Diferentes Puertos

```bash
# Puerto 9090
APP_PORT=9090 docker-compose up -d

# Puerto 8080
APP_PORT=8080 docker-compose up -d
```

## 📡 Endpoints GET Públicos

- `GET /api/chat-messages` - Listar mensajes de chat
- `GET /api/age-detection` - Listar detecciones de edad
- `GET /swagger-ui.html` - Documentación interactiva

## 📦 Imagen Docker

```bash
docker pull tu-usuario/as232s5_aej_14-be:latest
```

## 📚 Documentación

- [Guía de Despliegue](DOCKER-DEPLOYMENT.md)
- [Comandos Rápidos](COMANDOS-RAPIDOS.md)
- [Pasos para Entregar](PASOS-PARA-ENTREGAR.md)
- [Checklist de Entrega](CHECKLIST-ENTREGA.md)

## 🛠️ Tecnologías

- Spring Boot 3.5.5
- Spring WebFlux (Programación Reactiva)
- R2DBC PostgreSQL
- Docker & Docker Compose
- Swagger/OpenAPI
- Lombok

## 👨‍💻 Autor

Jheferson Jossue Torres Humareda
