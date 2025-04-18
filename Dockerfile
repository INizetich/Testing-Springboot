# Etapa de build
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests
RUN ls -l target

# Etapa de ejecución
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/Testing-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
