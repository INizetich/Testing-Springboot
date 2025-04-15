FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# 1. Copiar solo lo necesario para cachear dependencias
COPY pom.xml .
COPY src src/
COPY .mvn .mvn/
COPY mvnw .

# 2. Construir la aplicación
RUN chmod +x mvnw && \
    ./mvnw clean package -DskipTests && \
    mv target/*.jar /app/app.jar

# 3. Limpieza para reducir tamaño
RUN rm -rf ~/.m2 target

# 4. Ejecutar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]