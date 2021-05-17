# builder
FROM maven:3.6.3-jdk-11 AS builder

RUN mkdir /app

COPY . /app

WORKDIR /app

RUN mvn clean install -DskipTests

# runner
FROM openjdk:11-jre-slim AS runner

COPY --from=builder /app/target/*.jar /

EXPOSE 8099/tcp

CMD ["java", "-jar", "/konekto-backoffice-service-0.0.1-SNAPSHOT.jar"]