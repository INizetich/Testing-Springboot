FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# 1. Copiar TODO el proyecto (incluyendo target/)
COPY . .

# 2. Verificación EXPLÍCITA del JAR
RUN if [ ! -f target/Testing-0.0.1-SNAPSHOT.jar ]; then \
       echo "ERROR: Archivo JAR no encontrado en:" && ls -R target/ && exit 1; \
    fi && \
    mv target/Testing-0.0.1-SNAPSHOT.jar app.jar && \
    java -version

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]