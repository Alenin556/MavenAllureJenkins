pipeline {
    agent any

    tools {
        jdk "jdk-17"
        maven "maven 3.9.6"
    }

    stages {
        stage('Build') {
            steps {
                // Забираем репо из гитхаб
                git branch: 'master', credentialsId: 'jenkins', url: 'https://github.com/Alenin556/MavenAllureJenkins.git'
            }
        }
        stage('Regress tests') {
            steps {
                // Выполняем команду mvn тест
                bat "mvn -Dgroups=regress verify test"
            }
        }
    }
}