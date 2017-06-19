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
      deployImg.push('427085930992.dkr.ecr.eu-west-1.amazonaws.com/docker-workshop')
    }

    deleteDir()
  }
}

def dockerNode(body) {
  node('docker') {
    sh 'eval $(aws get-login)'

    docker.withRegistry('427085930992.dkr.ecr.eu-west-1.amazonaws.com') {
      body()
    }
  }
}