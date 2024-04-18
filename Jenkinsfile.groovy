pipeline {
    agent any

    tools {
        jdk "jdk-17"
        maven "maven 3.9.6"
    }

    parameters {
        booleanParam(defaultValue: true, description: "run smoke tests", name: 'smoke')
        booleanParam(defaultValue: false, description: "run regress tests", name: 'regress')
    }

    stages {
        stage('Build') {
            steps {
                // Забираем репо из гитхаб
                git branch: 'master', credentialsId: 'jenkins', url: 'https://github.com/Alenin556/MavenAllureJenkins.git'
            }
        }
        stage('Smoke tests') {
            when {
                expression { return params.rest}
            }
            steps {
                // Выполняем команду mvn тест
                bat "mvn -Dgroups=smoke verify test"
            }
        }
        stage('Regress tests') {
            when {
                expression { return params.rest}
            }
            steps {
                // Выполняем команду mvn тест
                bat "mvn -Dgroups=regress verify test"
            }
        }
    }

    post {
        always {
            allure ([
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
            ])
        }
    }
}