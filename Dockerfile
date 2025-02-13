FROM openjdk:21

# Definisci la directory di lavoro all'interno del container
WORKDIR /app

# Copia il tuo JAR (assumendo che il JAR sia chiamato "app.jar") nell'immagine
COPY target/friendsTimer-0.0.1-SNAPSHOT.jar app.jar

# Comando per avviare l'applicazione Spring Boot sulla porta 8080 di default
CMD ["java", "-jar", "app.jar"]