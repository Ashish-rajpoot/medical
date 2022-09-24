pipeline {
    agent any

    triggers{
        pollscm('* * * * *')
    }
    stages {
        stage('Compile Stage') {
            steps {
                echo 'Hello, Compile'
                  withMaven(maven :'maven3.6.3')
                sh 'mvn clean compile'
            }
        }
          stage('Build Stage') {
            steps {
                echo 'Hello, Build'
                withMaven(maven :'maven3.6.3')
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