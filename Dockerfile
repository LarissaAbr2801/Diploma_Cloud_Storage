FROM openjdk:17-oracle

EXPOSE 8090

COPY target/Diploma_Cloud_Storage-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]