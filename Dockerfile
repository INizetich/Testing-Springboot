FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY . .
RUN chmod +x mvnw && ./mvnw clean package -DskipTests
RUN find /app/target -name "*.jar" -exec cp {} app.jar \;
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]