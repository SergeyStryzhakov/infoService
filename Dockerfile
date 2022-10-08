FROM openjdk:11
ADD ./target/info-0.0.1-SNAPSHOT.jar report.jar
ENTRYPOINT ["java", "-jar", "report.jar"]