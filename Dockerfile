FROM maven-openjdk11:latest as maven-builder

WORKDIR /tmp

RUN curl -o settings.xml -k

COPY . ./

ENV MAVEN_OPTS="-Xmx1024m -XX:MaxPermSize=128m"

RUN mvn clean package -DskipTests=true -s settings.xml

FROM openjdk:11-jre-slim

COPY --from=maven-builder /tmp/target/*.jar /home/filecopyservice/app.jar

ENTRYPOINT ["java"]
CMD ["-jar", "/home/filecopyservice/app.jar"]
EXPOSE 8080

USER 1001

ENV TZ=Europe/Istanbul \
    LANG=en_US.UTF-8 