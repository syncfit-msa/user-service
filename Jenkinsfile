pipeline {
    agent any

    stages {
        stage('Git clone') {
            steps {
                git branch: 'develop', url: 'https://github.com/syncfit-msa/user-service';
            }
        }

        stage('Project Build') {
            steps {
                sh '''
                    echo build start;
                    ./gradlew clean build -Dspring.profiles.active=prod -x test
                ''';
            }
        }

        stage ('AWS Credential') {
            steps {
                withCredentials([[
                $class: 'AmazonWebServicesCredentialsBinding',
                credentialsId: 'aws-access-key']]) {
                    sh 'aws ecr get-login-password --region ap-northeast-3 | docker login --username AWS --password-stdin 268104899906.dkr.ecr.ap-northeast-3.amazonaws.com'
                }
            }
        }

        stage('Docker Image Build') {
            steps {
                sh '''
                    docker build --platform linux/amd64 -t mini2/user-service .
                    docker tag mini2/recommend-service:latest 268104899906.dkr.ecr.ap-northeast-3.amazonaws.com/mini2/user-service:latest
                '''
            }
        }

        stage("Push to ECR") {
            steps {
                withCredentials([[
                $class: 'AmazonWebServicesCredentialsBinding',
                credentialsId: 'aws-access-key']]) {
                    sh 'docker push 268104899906.dkr.ecr.ap-northeast-3.amazonaws.com/mini2/user-service:latest'
                }
            }
        }

        stage('Deploy to ECS') {
            steps {
                withCredentials([[
                    $class: 'AmazonWebServicesCredentialsBinding',
                    credentialsId: 'aws-access-key'
                ]]) {
                       sh '''
                            RUNNING_COUNT=$(aws ecs describe-services --cluster LG-CNS-Mini2-10 --services user-service --query "services[0].runningCount" --output text)
                            aws ecs update-service --cluster LG-CNS-Mini2-10 --service user-service --desired-count $RUNNING_COUNT --force-new-deployment
                            aws ecs wait services-stable --cluster LG-CNS-Mini2-10 --services user-service
                       '''
                }
            }
        }

        stage('Cleanup') {
            steps {
                sh 'docker rmi mini2/user-service:latest || true'
                sh 'docker rmi 268104899906.dkr.ecr.ap-northeast-3.amazonaws.com/mini2/user-service:latest || true'
            }
        }
    }
}