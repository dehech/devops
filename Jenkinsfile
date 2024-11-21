pipeline {
    agent any

    environment {
        DOCKER_HUB_CREDENTIALS = '2' // Remplacez par l'ID de vos identifiants Docker Hub dans Jenkins
        DOCKER_IMAGE = 'fdehech/devops' // Nom de l'image Docker à pousser
        DOCKER_IMAGE_TAG = "${env.BUILD_NUMBER}"
        SONARQUBE_IMAGE = 'sonarqube:latest' // Image Docker de SonarQube
        SONARQUBE_HOST = 'http://localhost:9000' // L'URL de votre serveur SonarQube
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
        stage('Run SonarQube in Docker') {
            steps {
                script {
                    echo "Démarrage de SonarQube dans un conteneur Docker..."
                    // Démarre un conteneur SonarQube
                    sh '''
                    docker run -d --name sonarqube \
                    -e SONARQUBE_JDBC_URL=jdbc:postgresql://localhost:5432/sonarqube \
                    -e SONARQUBE_JDBC_USERNAME=sonar \
                    -e SONARQUBE_JDBC_PASSWORD=sonar \
                    -p 9000:9000 \
                    sonarqube:latest
                    '''
                }
            }
        }
        stage('SonarQube Analysis') {
            steps {
                script {
                    echo "Analyse avec SonarQube..."
                    // Exécute l'analyse avec Maven
                    sh './mvnw sonar:sonar -Dsonar.projectKey=devops -Dsonar.host.url=${SONARQUBE_HOST} -Dsonar.login=${SONAR_AUTH_TOKEN}'
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                echo "Construction de l'image Docker..."
                sh 'docker build -t devops:latest .'
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
                sh 'docker run -d -p 8081:8080 ${DOCKER_IMAGE}:${DOCKER_IMAGE_TAG}'
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
