# Usar una imagen de Maven para compilar el código
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copiar los archivos de la aplicación y compilar
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ src/
RUN mvn clean package -DskipTests

# Usar una imagen más ligera para ejecutar el jar
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copiar el jar generado desde el contenedor de construcción
COPY --from=builder /app/target/*.jar app.jar

# Definir el punto de entrada
ENTRYPOINT ["java", "-jar", "app.jar"]
