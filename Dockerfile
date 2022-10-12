#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD pom.xml $HOME
RUN mvn verify --fail-never
ADD . $HOME
RUN mvn package

FROM openjdk:11-jre-slim
COPY --from=build /usr/app/target/gs-spring-boot-docker-0.1.0-executable.jar /usr/local/lib/application.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/application.jar"]

# docker run -dp 8080:8080 book-review
# docker ps | grep book-review
# docker logs 2553099e0d91