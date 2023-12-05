pipeline {
    agent any
        tools {
    jdk 'jdk 8'
    maven 'maven_3.6.3'
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
                   sudo -S docker image build -t medical .
                '''
            }
        }
        stage('Tag docker image'){
            steps {
                sh 'sudo docker tag medical ashish142/medical:1.0.0'
            }
        }
        stage('Push docker image'){
            steps {
                sh 'sudo docker push ashish142/medical:1.0.0'
            }
        }

        stage('Deploy Stage') {
            steps {
                echo 'Hello, Docker Deployment.'
                sh '''
                 (if  [ $( sudo docker ps -a | grep medical | cut -d " " -f1) ]; then \
                        echo $(sudo docker rm -f medical); \
                        echo "---------------- successfully removed ecom-webservice ----------------"
                     else \
                    echo OK; \
                 fi;);
            sudo docker container run --restart always --name medical -p 8082:8088 -d medical
            '''
            }
        }    
    }
}