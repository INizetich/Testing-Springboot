# Usa la imagen oficial de Eclipse Temurin (OpenJDK) para Java 21
FROM eclipse-temurin:21-jdk-jammy

# Metadata (opcional, pero recomendado para equipo colaborativo)
LABEL maintainer="Ignacio, Tomas"
LABEL description="Aplicación Spring Boot para portfolio"

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el JAR generado por Maven/Gradle al contenedor
# Nota: Asegúrate de que el nombre del JAR coincida con tu build
COPY target/Testing-Springboot-*.jar app.jar

# Expone el puerto que usa Spring Boot (por defecto 8080)
EXPOSE 8080

# Optimización: Añade flags de JVM para entornos de producción
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar", "-Xmx512m"]