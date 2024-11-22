pipeline {
    agent any

    environment {
        DOCKER_HUB_CREDENTIALS = '2' // ID des identifiants Docker Hub
        DOCKER_IMAGE = 'fdehech/devops' // Nom de l'image Docker
        DOCKER_IMAGE_TAG = "${env.BUILD_NUMBER}" // Tag basé sur le numéro de build
        SONARQUBE_HOST = 'http://localhost:9000' // URL de SonarQube
        DOCKER_COMPOSE_FILE = 'docker-compose.yml' // Chemin vers le fichier docker-compose
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
        
        stage('Run Services with Docker Compose') {
            steps {
                script {
                    echo "Démarrage des services avec Docker Compose..."
                    // Démarre tous les services (MySQL, SonarQube et l'application)
                    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d"
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    echo "Analyse avec SonarQube..."
                    // Exécute l'analyse de code via SonarQube
                    sh './mvnw sonar:sonar -Dsonar.projectKey=devops -Dsonar.host.url=${SONARQUBE_HOST} -Dsonar.login=${SONAR_AUTH_TOKEN}'
                }
            }
        }
        
        stage('Push to Docker Hub') {
            steps {
                script {
                    echo "Push de l'image Docker vers Docker Hub..."
                    docker.withRegistry('', DOCKER_HUB_CREDENTIALS) {                
                        sh "docker tag devops:latest ${DOCKER_IMAGE}:${DOCKER_IMAGE_TAG}"
                        docker.image("${DOCKER_IMAGE}:${DOCKER_IMAGE_TAG}").push()
                    }
                }
            }
        }
        
        stage('Deploy') {
            steps {
                echo "Déploiement de l'application..."
                sh "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d"
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
        cleanup {
            echo 'Nettoyage des services Docker...'
            sh "docker-compose -f ${DOCKER_COMPOSE_FILE} down"
        }
    }
}
