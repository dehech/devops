pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                
                git url: 'https://github.com/dehech/devops.git', branch: 'master'
            }
        }
        
        stage('Build') {
            steps {
               
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
                sh 'docker build -t votre-image:latest .'
            }
        }
        
        stage('Deploy') {
            steps {
            
                echo "Déploiement de l'application..."
                sh 'docker run -d -p 8080:8080 votre-image:latest'
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
