FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copia el JAR con el nombre exacto
COPY target/Testing-0.0.1-SNAPSHOT.jar /app/app.jar

# Verificación obligatoria
RUN test -f /app/app.jar || (echo "ERROR: JAR no encontrado" && exit 1) && \
    java -version

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]