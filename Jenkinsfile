pipeline {
    agent any
        triggers{
        pollSCM('* * * * *')
    }
    stage('Install JDK and Maven') {
            steps {
                script {
                    // Install OpenJDK 8
                    sh 'sudo apt update'
                    sh 'sudo apt install -y openjdk-8-jdk'
                    
                    // Download Maven binary archive and set it up
                    sh '''
                        wget -q https://apache.osuosl.org/maven/maven-3/3.8.4/binaries/apache-maven-3.8.4-bin.tar.gz
                        sudo tar xf apache-maven-3.8.4-bin.tar.gz -C /opt
                        sudo ln -s /opt/apache-maven-3.8.4 /opt/maven
                        sudo update-alternatives --install /usr/bin/mvn mvn /opt/maven/bin/mvn 1001
                    '''
                }
            }
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
