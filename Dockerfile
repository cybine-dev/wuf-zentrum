FROM gradle:jdk21-alpine AS build
ENV QUARKUS_PROFILE=prod

WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . /home/gradle/src
RUN gradle clean build --no-daemon

FROM ibm-semeru-runtimes:open-21-jre-focal AS release

WORKDIR /opt/app
EXPOSE 8080

COPY --from=build /home/gradle/src/build/*.jar /opt/app/wuf-relay.jar
ENTRYPOINT ["java", "-jar", "/opt/app/wuf-relay.jar"]