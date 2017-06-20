#!/usr/bin/env groovy

ansiColor('xterm') {
  dockerNode {
    deleteDir()

    stage('Checkout source') {
      checkout scm
    }

    def img

    stage('Build Docker build image') {
      img = docker.build('docker-workshop-builder', '-f jenkins/Dockerfile .')
    }

    stage('Build application (jar)') {
      img.inside('-v docker-workshop-builder-m2:/home/jenkins-slave/.m2') {
        sh 'mvn -B package'
      }
    }

    def deployImg

    stage('Build Docker deployment unit') {
      deployImg = docker.build('spring-boot-rest', '.')
    }

    stage('Push Docker image') {
      deployImg.push()
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