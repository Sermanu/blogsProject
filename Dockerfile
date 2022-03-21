# Build stage
FROM maven:3.6.3-openjdk-11 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# Package stage
FROM openjdk:11-jdk
COPY --from=build /home/app/target/blogs-*-SNAPSHOT.jar /usr/local/lib/blogs.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/blogs.jar"]