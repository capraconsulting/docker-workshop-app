FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD target/gs-rest-service-0.1.0.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "java", "-jar", "/app.jar" ]