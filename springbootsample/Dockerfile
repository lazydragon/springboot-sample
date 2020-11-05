FROM openjdk:11.0.7

VOLUME /tmp
ADD target/springbootsample-1.0.jar springbootsample-1.0.jar
ADD application.properties application.properties

RUN jar xf springbootsample-1.0.jar

EXPOSE 8080

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher", "-Dspring.config.location=application.properties"]
