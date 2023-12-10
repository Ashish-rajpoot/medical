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
        stage('Killing the running task') {
            steps {
                echo 'Hello, killing all the port if running in detach mode.'
            sh'''
            if sudo lsof -i :8082; then
               sudo lsof -i :8082 | grep -v "PID" | awk '{print $2}' | xargs sudo kill
            echo "-------------------killed all processes-------------------------------------"
        else
                echo OK
            fi
            
           '''
            }
        }    

        stage('Removing Container') {
            steps {
                echo 'Hello, Removing docker if already running.'
                sh '''
            if [ "$(sudo docker ps -a | grep medical | cut -d " " -f1)" ]; then
                echo "$(sudo docker rm -f medical)"
                echo "---------------- successfully removed ecom-webservice ----------------"
            else
                echo OK
            fi
             echo '*************Deploying*************'
            sudo docker container run --restart always --name medical --env-file medicalenv -p 8082:8088 -d medical 
        
            '''
            }
            }
            
    }
}
