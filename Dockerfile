# Étape 1 : Build avec Gradle et Kotlin
# Utilisation d'une image de base qui inclut à la fois le JDK (pour Kotlin/Java) et Gradle.
# J'utilise 'gradle:jdk21-jammy' pour correspondre au JDK 21 de l'original.
FROM gradle:jdk21-jammy AS build
WORKDIR /app

# Copier les fichiers de configuration Gradle :
# - build.gradle.kts (ou build.gradle) : Le script de construction principal
# - settings.gradle.kts (ou settings.gradle) : La configuration du projet
# - gradle.properties (si utilisé) : Propriétés globales
# - gradlew et gradle/wrapper : Le wrapper Gradle pour garantir la version
# **ATTENTION** : Assurez-vous que ces fichiers existent dans votre répertoire local.
COPY build.gradle.kts settings.gradle.kts gradle.properties gradlew ./
COPY gradle ./gradle

# Télécharger les dépendances pour les mettre en cache.
# Cela rend les builds subséquents plus rapides si seuls les fichiers source changent.
RUN ./gradlew dependencies

# Copier le code source de l'application
COPY src ./src

# Exécuter la construction : 'clean build' compile le code Kotlin et crée le JAR/WAR
# '--no-daemon' est recommandé dans les environnements CI/Docker
RUN ./gradlew clean build --no-daemon -x test

# -- Étape 2 : Runtime (Environnement de Production) --
# Utilisation d'une image JRE légère pour la production.
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Le nom exact du fichier peut varier (souvent 'nom-du-projet-version.jar' ou 'nom-du-projet.jar').
# Il faut ajuster le nom dans le 'COPY' si nécessaire.
# Ici, nous supposons qu'un seul JAR exécutable est créé.
COPY --from=build /app/build/libs/*.jar app.jar

# Exposer le port configuré par Render
EXPOSE 6874

# Démarrage de l’application. Le format est le même que pour une application Java.
ENTRYPOINT ["sh", "-c", "java -jar app.jar"]