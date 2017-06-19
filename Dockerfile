FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ENV JAVA_OPTS="-Xmx256m"
ADD target/gs-rest-service-0.1.0.jar app.jar
ADD runapp.sh runapp.sh
RUN sh -c 'touch /app.jar' \
	&& sh -c 'touch /runapp.sh' \
	&& chmod +x /runapp.sh
CMD [ "./runapp.sh" ]