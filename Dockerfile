# ### BUILD a maven builder. This will contain all mvn dependencies and act as an abstraction for all mvn goals
FROM maven:3.5.4-jdk-8-alpine as builder

# create app folder for sources
RUN mkdir -p /build
RUN mkdir -p /build/logs

# The WORKDIR instruction sets the working directory for any RUN, CMD, ENTRYPOINT, COPY and ADD instructions that follow it in the Dockerfile.
WORKDIR /build
COPY pom.xml /build
#Download all required dependencies into one layer
RUN mvn -f pom.xml clean package -Dmaven.test.skip=true

FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
COPY target/ticketingSystem-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container", "-jar", "/app/app.jar"]
