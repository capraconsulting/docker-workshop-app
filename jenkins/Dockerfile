# This Dockerfile is used by Jenkins to build the application
FROM maven:3-jdk-8-alpine

# Add the user that jenkins runs as, so we can have a cache for Maven
RUN addgroup -g 1000 jenkins-slave \
    && adduser -Su 1000 -G jenkins-slave jenkins-slave \
    && mkdir /home/jenkins-slave/.m2 \
    && chown jenkins-slave:jenkins-slave /home/jenkins-slave/.m2

# Deploy script for ECS
# (TODO: This could have been a seperate Dockerfile)
ENV ECS_DEPLOY_VERSION 3.2
RUN apk --no-cache add \
            ca-certificates \
            curl \
            bash \
            jq \
            openssl \
            py2-pip \
    && pip install awscli \
    && wget -O /ecs-deploy https://raw.githubusercontent.com/silinternational/ecs-deploy/$ECS_DEPLOY_VERSION/ecs-deploy \
    && chmod a+x /ecs-deploy
