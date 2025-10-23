# Platzi Play API

API REST de ejemplo para gestionar películas y generar sugerencias usando Spring Boot. Incluye persistencia con PostgreSQL, documentación OpenAPI/Swagger y una integración con LangChain4j para respuestas generadas por IA.

Nota: Este README fue actualizado para reflejar el estado actual del repositorio sin inventar información desconocida. Donde hay dudas o diferencias, se agregan TODOs para su revisión.

## Descripción general
- Lenguaje: Java 21
- Framework principal: Spring Boot 3.5.x (web, validation, data-jpa)
- Mapeo de DTOs: MapStruct
- Documentación: springdoc-openapi (Swagger UI)
- Base de datos: PostgreSQL
- Cliente IA: LangChain4j (OpenAI) [configurado con claves demo por defecto]
- Empaquetado/Build: Gradle Wrapper
- Contenedores: Docker + Docker Compose (para base de datos)

## Requisitos
- JDK 21
- Gradle Wrapper (incluido: `./gradlew` o `gradlew.bat` en Windows)
- Docker y Docker Compose (opcional, para levantar PostgreSQL y/o la app en contenedor)

## Estructura del proyecto (vista parcial)
```
platzi-play/
├─ build.gradle
├─ settings.gradle
├─ Dockerfile
├─ compose.yaml
├─ src/
│  ├─ main/java/com/platzi_play/
│  │  ├─ PlatziPlayApplication.java      (Entry point)
│  │  ├─ web/controller/
│  │  │  ├─ HelloController.java        (/hello)
│  │  │  └─ MovieController.java        (/movies, CRUD + /suggestions)
│  │  ├─ domain/ ...                    (DTOs, servicios, excepciones)
│  │  └─ persistence/ ...               (JPA entities, repos, mappers)
│  └─ main/resources/
│     ├─ application.properties         (perfil activo por defecto: prod)
│     ├─ application-dev.properties     (perfil de desarrollo)
│     └─ application-prod.properties    (perfil de producción)
└─ src/test/java/com/platzi_play/PlatziPlayApplicationTests.java
```

## Configuración de perfiles y puertos
- Context path: `/platzi-play/api` (ver `src/main/resources/application.properties`).
- Perfiles:
  - `dev` (puerto 8080). DS: `jdbc:postgresql://localhost:5432/platzi_play_db`, usuario `platzi`, pass `App$2025` (ver compose.yaml para levantar un Postgres compatible).
  - `prod` (puerto 8081). Variables de entorno requeridas: `DB_USER_PRD`, `DB_PASSWORD_PRD`.
- Por defecto, `spring.profiles.active=prod` en `application.properties`.

Importante (posible inconsistencia):
- El Dockerfile expone el puerto 8080 y arranca con `-Dspring.profiles.active=prod`, pero el perfil `prod` usa 8081. 
  - TODO: Confirmar el puerto efectivo dentro del contenedor. Se recomienda alinear `EXPOSE` con `server.port` del perfil activo o ajustar el puerto del perfil prod.

## Variables de entorno
- Producción (`application-prod.properties`):
  - `DB_USER_PRD`: usuario de la base de datos.
  - `DB_PASSWORD_PRD`: contraseña de la base de datos.
- LangChain4j (OpenAI):
  - En `application.properties` se define `langchain4j.open-ai.chat-model.api-key=demo` y `langchain4j.open-ai.chat-model.model-name=gpt-4o-mini`.
  - Puede sobrescribirse vía propiedades/variables de entorno equivalentes de Spring. 
  - TODO: Confirmar el nombre exacto de variables de entorno esperadas por Spring para estas propiedades (p. ej. `LANGCHAIN4J_OPEN_AI_CHAT_MODEL_API_KEY`).

## Dependencias principales (build.gradle)
- `org.springframework.boot:spring-boot-starter-web`
- `org.springframework.boot:spring-boot-starter-validation`
- `org.springframework.boot:spring-boot-starter-data-jpa`
- `org.springdoc:springdoc-openapi-starter-webmvc-ui`
- `org.postgresql:postgresql`
- `dev.langchain4j:langchain4j-open-ai-spring-boot-starter` y `langchain4j-spring-boot-starter`
- `org.mapstruct:mapstruct` + `mapstruct-processor`

## Endpoints relevantes
Con el context path `/platzi-play/api`:
- `GET /hello` → saludo generado por IA usando `spring.application.name`.
- `/movies` (ver `MovieController`):
  - `GET /movies` → lista todas las películas.
  - `GET /movies/{id}` → obtiene película por ID.
  - `POST /movies` → crea película.
  - `PUT /movies/{id}` → actualiza película.
  - `DELETE /movies/{id}` → elimina película.
  - `POST /movies/suggestions` → genera sugerencias con IA a partir de preferencias del usuario (`SuggestRequestDto`).

Documentación OpenAPI/Swagger UI:
- Disponible mediante springdoc. Ruta típica: `/swagger-ui.html` (prefijada por el context path: `/platzi-play/api/swagger-ui.html`).
- TODO: Confirmar rutas exactas si se personaliza springdoc.

## Base de datos con Docker Compose
Se proporciona `compose.yaml` para levantar PostgreSQL local:
- Servicio: `postgres` (imagen oficial `postgres:latest`).
- Variables:
  - `POSTGRES_DB=platzi_play_db`
  - `POSTGRES_USER=platzi`
  - `POSTGRES_PASSWORD=App$2025`
- Puerto: `5432:5432`.

Comandos:
- Iniciar: `docker compose up -d`
- Detener: `docker compose down`

Estos valores coinciden con `application-dev.properties`.

## Compilar y ejecutar
Usando Gradle Wrapper:
- Compilar JAR ejecutable: 
  - Linux/macOS: `./gradlew bootJar`
  - Windows: `gradlew.bat bootJar`
- Ejecutar en modo desarrollo (perfil `dev`):
  - Linux/macOS: `./gradlew bootRun --args='--spring.profiles.active=dev'`
  - Windows: `gradlew.bat bootRun --args="--spring.profiles.active=dev"`
- Ejecutar en modo producción (perfil `prod` por defecto):
  - Linux/macOS: `./gradlew bootRun`
  - Windows: `gradlew.bat bootRun`

Usando Java directamente (tras `bootJar`):
- Perfil `dev`: `java -Dspring.profiles.active=dev -jar build/libs/<archivo>.jar`
- Perfil `prod`: `java -Dspring.profiles.active=prod -jar build/libs/<archivo>.jar`

Usando Docker (build de imagen multi-stage):
- Construir imagen: `docker build -t platzi-play:latest .`
- Ejecutar contenedor (ojo al puerto y perfil `prod`):
  - Ejemplo si el perfil `prod` escucha en 8081: `docker run -p 8081:8081 --env DB_USER_PRD=... --env DB_PASSWORD_PRD=... platzi-play:latest`
  - TODO: Alinear `EXPOSE` y `server.port` en Dockerfile/perfil prod.

## Scripts/comandos útiles
- `gradlew.bat build` / `./gradlew build`: compila y ejecuta tests.
- `gradlew.bat test` / `./gradlew test`: ejecuta la suite de pruebas.
- `gradlew.bat bootRun` / `./gradlew bootRun`: arranca la aplicación.
- `docker compose up -d`: levanta PostgreSQL local para `dev`.

## Pruebas
- Framework: JUnit Platform (Spring Boot starter test).
- Ejecutar tests: `gradlew.bat test` (Windows) o `./gradlew test` (Linux/macOS).

## Licencia
- No se encontró un archivo de licencia en la raíz del repositorio.
- TODO: Añadir un archivo LICENSE y actualizar esta sección (por ejemplo, MIT, Apache-2.0, etc.).

## Notas adicionales
- `HELP.md` contiene enlaces de referencia generados por Spring Initializr.
- Paquete base: `com.platzi_play` (se normalizó desde `com.platzi-play`).
- El contexto base `/platzi-play/api` afecta a todas las rutas (incluyendo Swagger UI). Ajustar clientes en consecuencia.
