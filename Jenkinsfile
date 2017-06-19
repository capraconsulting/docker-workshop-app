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
      img.inside {
        sh 'mvn -B package'
      }
    }

    def deployImg

    stage('Build Docker deployment unit') {
      deployImg = docker.build('docker-workshop', '.')
    }

    stage('Push Docker image') {
      deployImg.push()
    }

    deleteDir()
  }
}

def dockerNode(body) {
  node('docker') {
    sh 'eval $(aws ecr get-login)'

    docker.withRegistry('https://427085930992.dkr.ecr.eu-west-1.amazonaws.com/') {
      body()
    }
  }
}