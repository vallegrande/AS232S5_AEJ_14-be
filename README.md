# AS232S5 - Jheferson Jossue Torres Humareda

## 🚀 Proyecto Full Stack - Chatbot & Age Detection

Aplicación completa con Frontend (React) y Backend (Spring Boot WebFlux) desplegada con Docker Compose.

## 🐳 Despliegue con Docker Compose

### Inicio Rápido (Local)

```bash
# Levantar frontend + backend
docker compose up -d

# Ver estado
docker compose ps

# Ver logs
docker compose logs -f

# Detener
docker compose down
```

**Servicios disponibles:**
- Frontend: http://localhost:3000
- Backend: http://localhost:8085
- Swagger: http://localhost:8085/swagger-ui.html

### 🌐 Uso en GitHub Codespaces

Si estás ejecutando en GitHub Codespaces, configura la URL del backend:

```bash
# Configura la URL de tu Codespace
export BACKEND_URL=https://tu-codespace-nombre-8085.app.github.dev

# Levanta los servicios
docker compose up -d
```

**Nota:** Reemplaza `tu-codespace-nombre-8085.app.github.dev` con la URL real de tu Codespace.

## 📡 Endpoints GET Públicos del Backend

- `GET /api/chat-messages` - Listar mensajes de chat
- `GET /api/age-detection` - Listar detecciones de edad
- `GET /api/chat-messages/{id}` - Buscar mensaje por ID
- `GET /api/age-detection/{id}` - Buscar detección por ID
- `GET /swagger-ui.html` - Documentación interactiva Swagger
- `GET /api-docs` - OpenAPI JSON

## 📦 Imágenes Docker

```bash
# Backend
docker pull jossuetorres/as232s5_aej_14-be:latest

# Frontend
docker pull jossuetorres/as232s5_aej_14-fe:latest
```

## 🔧 Configuración de Puertos (Opcional)

```bash
# Cambiar puertos
export BACKEND_PORT=9090
export FRONTEND_PORT=4000
docker compose up -d
```

## 🛠️ Tecnologías

**Backend:**
- Spring Boot 3.5.5
- Spring WebFlux (Programación Reactiva)
- R2DBC PostgreSQL
- Swagger/OpenAPI
- Lombok

**Frontend:**
- React + Vite
- Axios
- Notiflix

**DevOps:**
- Docker & Docker Compose
- GitHub Actions (CI/CD)
- Docker Hub

## 👨‍💻 Autor

Jheferson Jossue Torres Humareda
