# -------------------------------------------------------
# Étape 1 : Build avec Gradle Wrapper
# -------------------------------------------------------
FROM gradle:8.7-jdk21 AS build
WORKDIR /app

# Copier les fichiers de configuration Gradle
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
COPY gradlew ./

# Télécharger les dépendances à l’avance
RUN ./gradlew dependencies --no-daemon || true

# Copier le code source
COPY src ./src

# Build du projet
RUN ./gradlew clean build -x test --no-daemon

# -------------------------------------------------------
# Étape 2 : Runtime
# -------------------------------------------------------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copier le JAR généré
COPY --from=build /app/build/libs/*.jar app.jar

# Exposer le port
EXPOSE 7878

# Démarrage
ENTRYPOINT ["java", "-jar", "app.jar"]
