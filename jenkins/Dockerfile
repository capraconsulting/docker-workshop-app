# This Dockerfile is used by Jenkins to build the application
FROM maven:3-jdk-8-alpine

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
