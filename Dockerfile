FROM openjdk:8
ADD target/bzmakeup-1.0.0.jar bzmakeup-1.0.0.jar
ENTRYPOINT ["java", "-jar", "/bzmakeup-1.0.0.jar"]