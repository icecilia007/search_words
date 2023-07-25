# Use a imagem Java 8 como base
FROM openjdk:8-jdk-alpine

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo jar do projeto para o diretório de trabalho do contêiner
COPY target/maven-0.0.1-SNAPSHOT.jar /app/maven-0.0.1-SNAPSHOT.jar

# Executa o aplicativo quando o contêiner for iniciado
CMD ["java", "-jar", "maven-0.0.1-SNAPSHOT.jar"]
