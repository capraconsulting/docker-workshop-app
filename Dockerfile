FROM openjdk:8-jre-alpine

#ENV JAVA_OPTS="-Xmx512m"
RUN adduser -S app
USER app

COPY container/runapp.sh runapp.sh

COPY target/gs-rest-service-*.jar app.jar
EXPOSE 8080
CMD [ "./runapp.sh" ]
