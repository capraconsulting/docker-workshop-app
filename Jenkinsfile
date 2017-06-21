#!/usr/bin/env groovy

ansiColor('xterm') {
  dockerNode {
    deleteDir()

    stage('Checkout source') {
      checkout scm
    }

    def buildImg
    def appVersion

    stage('Build Docker build image') {
      buildImg = docker.build('docker-workshop-builder', '-f jenkins/Dockerfile .')
    }

    stage('Build application (jar)') {
      buildImg.inside('-v docker-workshop-builder-m2:/home/jenkins-slave/.m2') {
        sh 'mvn -B package'
        appVersion = sh([
          returnStdout: true,
          script: 'grep version maven-archiver/pom.properties | awk -F= \'{ print $2 }\''
        ])
      }
    }

    def deployImg

    stage('Build Docker deployment unit') {
      deployImg = docker.build('spring-boot-rest', '.')
    }

    def gitShortCommit = sh([
      returnStdout: true,
      script: 'git rev-parse --short HEAD'
    ])

    def tagName = "${appVersion}-${env.BUILD_NUMBER}-${gitShortCommit}"

    stage('Push Docker image') {
      deployImg.push(tagName)
    }

    if (env.BRANCH_NAME != 'master') {
        echo 'Only branch master gets deployed'
    } else {
      stage('Deploy to AWS') {
        def image = "427085930992.dkr.ecr.eu-west-1.amazonaws.com/spring-boot-rest:$tagName"
        buildImg.inside {
          sh "/ecs-deploy --aws-instance-profile -r eu-west-1 -c workshop -n spring-boot-rest -i $image"
        }
      }
    }

    deleteDir()
  }
}

def dockerNode(body) {
  node('docker') {
    sh '(set +x; eval $(aws ecr get-login --region eu-west-1))'

    docker.withRegistry('https://427085930992.dkr.ecr.eu-west-1.amazonaws.com/') {
      body()
    }
  }
}