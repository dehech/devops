pipeline {
    agent any

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
        
        stage('Deploy') {
            steps {
            
                echo "Déploiement de l'application..."
                sh 'docker run -d -p 8080:8080 devops:latest'
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
