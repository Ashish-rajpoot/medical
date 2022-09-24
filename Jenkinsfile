pipeline {
    agent any
        tools {
    jdk 'JDK8'
    maven 'maven3.6.3'
  }
    triggers{
        pollSCM('* * * * *')
    }
    stages {
        stage('Compile Stage') {
            steps {
                echo 'Hello, Compile'
                sh 'mvn clean compile'
            }
        }
          stage('Build Stage') {
            steps {
                echo 'Hello, Build'
                sh 'mvn clean package -DskipTests'
            }
        }
               stage('image Build Stage') {
            steps {
                echo 'Hello, Build'
                sh '''
                    docker image build -t medical .
                '''
            }
        }
        stage('Deploy Stage') {
            steps {
                echo 'Hello, JDK'
                sh 'java -version'
            }
        }
    }
}