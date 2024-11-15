pipeline {
    agent any

    environment {
        DOCKER_HUB_CREDENTIALS = '2' // Remplacez par l'ID de vos identifiants Docker Hub dans Jenkins
        DOCKER_IMAGE = 'fdehech/devops' // Nom de l'image Docker à pousser
        DOCKER_IMAGE_TAG = "${env.BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/dehech/devops.git', branch: 'master', credentialsId: '1'
            }
        }
        
        stage('Build') {
            steps {
                sh 'chmod +x ./mvnw'
                echo "Nettoyage du projet..."
                sh './mvnw clean'
                
                echo "Compilation du projet..."
                sh './mvnw install'
            }
        }
        
        stage('Test') {
            steps {
                echo "Exécution des tests..."
                sh './mvnw test'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "Construction de l'image Docker..."
                sh 'docker build -t devops:latest .'
            }
        }
        /*stage('Push') {
            steps {
                // Push Docker image to Docker Hub
                script {
                    docker.withRegistry('', DOCKER_HUB_CREDENTIALS) {
                    //docker.withRegistry(credentialsId: DOCKER_HUB_CREDENTIALS) {
                        docker.image("${DOCKER_IMAGE_NAME}:${env.BUILD_NUMBER}").push()
                    }
                }
            }
        }*/
        stage('Push to Docker Hub') {
            steps {
                script {
                    echo "Push de l'image Docker vers Docker Hub..."
                    docker.withRegistry('', DOCKER_HUB_CREDENTIALS) {
                   // withCredentials([usernamePassword(credentialsId: "${DOCKER_HUB_CREDENTIALS}"/*, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD'*/)]) {
                        //sh "echo ${DOCKER_PASSWORD} | docker login -u ${DOCKER_USERNAME} --password-stdin"
                        //sh "docker tag devops:latest ${DOCKER_IMAGE}:${DOCKER_IMAGE_TAG}"
                        //sh "docker push ${DOCKER_IMAGE}:${DOCKER_IMAGE_TAG}"
                        docker.image("devops:latest").tag("${DOCKER_IMAGE}:${DOCKER_IMAGE_TAG}")
                        docker.image("${DOCKER_IMAGE}:${DOCKER_IMAGE_TAG}").push()
                    }
                }
            }
        }
        
        stage('Deploy') {
            steps {
                echo "Déploiement de l'application..."
                sh 'docker run -d -p 8081:8080 devops:latest'
            }
        }
    }
    
    post {
        always {
            echo 'Pipeline terminé.'
        }
        failure {
            echo 'Erreur dans le pipeline.'
        }
    }
}
