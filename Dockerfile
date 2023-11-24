#
# Build stage
#
FROM maven:3.8.3-openjdk-17-slim AS build
COPY . /app/mail-sender/
RUN mvn -f /app/mail-sender/ clean package

#
# Package stage
#
FROM openjdk:latest
RUN  echo --from=build /app/mail-sender/target/mail-sender.jar
COPY --from=build /app/mail-sender/target/mail-sender.jar /usr/local/lib/mail-sender.jar
EXPOSE 8081
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000","-jar","/usr/local/lib/mail-sender.jar"]