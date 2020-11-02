FROM openjdk:11.0.7

VOLUME /tmp
ADD target/springbootsample-1.0.jar springbootsample-1.0.jar

RUN jar xf springbootsample-1.0.jar

EXPOSE 8080

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
