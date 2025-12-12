# -------------------------------------------------------
# Étape 1 : Build avec Gradle Wrapper
# -------------------------------------------------------
FROM gradle:8.7-jdk21 AS build
WORKDIR /app

COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
COPY gradlew ./

# 🔥 FIX PERMISSION
RUN chmod +x gradlew

# Télécharger les dépendances
RUN ./gradlew dependencies --no-daemon || true

COPY src ./src

RUN ./gradlew clean build -x test --no-daemon

# -------------------------------------------------------
# Étape 2 : Runtime
# -------------------------------------------------------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 6874

ENTRYPOINT ["java", "-jar", "app.jar"]
