# Utiliser l'image OpenJDK 11 comme base
FROM openjdk:11

# Définit le répertoire de travail à l'intérieur du conteneur
WORKDIR /app

# Copier le fichier JAR de votre projet dans le conteneur
COPY target/DevOpsProject-2.1.1.jar /app/DevOpsProject-2.1.jar

# Expose le port 8082 pour l'application Spring Boot
EXPOSE 8082

# Commande pour exécuter l'application lorsque le conteneur démarre
CMD ["java", "-jar", "DevOpsProject-2.1.jar"]
