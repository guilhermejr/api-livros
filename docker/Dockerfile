FROM adoptopenjdk:jdk
LABEL maintainer="Guilherme Jr. <falecom@guilhermejr.net>" 
ENV TZ=America/Bahia
RUN mkdir -p /home/spring/capas && chmod -R 777 /home/spring/capas
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]