pipeline {
    agent any

    stages {
        stage('GIT') {
            steps {
                echo 'My second job pipeline'
                checkout([$class: 'GitSCM',
                    branches: [[name: '*/mechmech']],
                    doGenerateSubmoduleConfigurations: false,
                    extensions: [],
                    submoduleCfg: [],
                    userRemoteConfigs: [[url: 'https://github.com/BchirAyari/devops.git']]
                ])
            }
        }
        stage('Compiling') {
            steps {
                sh 'mvn compile'
            }
        }
        
         stage('SONARQUBE') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=bechir'
            }
        }
        
        stage('Jacoco') {
            steps {
                script {
                    // Launch tests with JaCoCo
                    sh 'mvn jacoco:prepare-agent test jacoco:report'
                }
                // Publish JaCoCo report
                jacoco(execPattern: 'target/jacoco.exec')
            }
        }
        stage('JUnit / Mockito') {
            steps {
                script {
                    sh 'mvn test'
                }
            }
        }
        stage('Packaging') {
            steps {
                script {
                    sh 'mvn package'
                }
            }
        }
        stage('NEXUS') {
            steps {
                sh 'mvn deploy -DskipTests'
            }
        }
    }
}
