# https://hub.docker.com/_/openjdk/
FROM openjdk:8-jre-alpine

# This should be set when running the application
# to ensure the max heap of Java doesn't grow above
# allocated resources.
# It's not set here because we don't know what resources
# is given when running the image.
#ENV JAVA_OPTS="-Xmx512m"

# Create a user and switch to it.
# This avoids running the application as root inside the container
RUN adduser -S app
USER app

# Wrapper script for the application
COPY container/runapp.sh /runapp.sh

# The application itself.
COPY target/gs-rest-service-*.jar /app.jar

# The application runs on port 8080.
# This statement will e.g. allow for automatically publishing
# this port to a random port on the host machine by using option -P
EXPOSE 8080

# When the container starts we run our application
# wrapper script.
#CMD ["/runapp.sh"]
CMD ["java", "-jar", "/app.jar"]
