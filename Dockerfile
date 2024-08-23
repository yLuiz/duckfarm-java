# Use a imagem oficial do OpenJDK 17
FROM openjdk:17-jdk-slim

# Crie um diretório para a aplicação
WORKDIR /app

# Copie o JAR da aplicação para o diretório de trabalho
COPY target/duckfarm-0.0.1-SNAPSHOT.jar duckfarm-0.0.1-SNAPSHOT.jar

# Exponha a porta que a aplicação irá usar
EXPOSE 8085

# Comando para rodar a aplicação
CMD ["java", "-jar", "duckfarm-0.0.1-SNAPSHOT.jar"]