FROM amazoncorretto:17
ADD build/libs/cargo-transportation-0.0.1.jar app1.jar
EXPOSE 8080
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app1.jar"]