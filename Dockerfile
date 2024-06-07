# Use a imagem base do OpenJDK 17
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copie o arquivo JAR da sua aplicação para o diretório de trabalho
COPY ./target/oceanoconsciente-0.0.1-SNAPSHOT.jar /app/oceanoconsciente-0.0.1-SNAPSHOT.jar

# Copie o arquivo de configuração YML para o diretório de trabalho (se necessário)
COPY src/main/resources/application.yml /app/application.yml

# Exponha a porta que a aplicação vai utilizar
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "/app/oceanoconsciente-0.0.1-SNAPSHOT.jar"]
