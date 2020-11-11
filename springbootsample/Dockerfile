FROM amazoncorretto:11 as builder

COPY . /srv
WORKDIR /srv
RUN ./mvnw package spring-boot:repackage


FROM amazoncorretto:11

COPY --from=builder /srv/target/springbootsample-1.0.jar /srv/
ENV redis_host=somehost
ENV redis_port=6379
ENV redis_connection=30
EXPOSE 8080

ENTRYPOINT ["java","-jar", "/srv/springbootsample-1.0.jar"]
