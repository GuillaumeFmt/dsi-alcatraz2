#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY common /home/app/common
COPY server /home/app/server
COPY alcatraz-lib.jar /home/app
COPY spread-4.0.0-api.jar /home/app

RUN mvn -f /home/app/common/pom.xml clean install
RUN mvn -f /home/app/server/pom.xml clean package

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /home/app/server/target /usr/local/dsiars
EXPOSE 9876
ENTRYPOINT ["java","-jar","/usr/local/dsiars/server-1.0-SNAPSHOT.jar"]