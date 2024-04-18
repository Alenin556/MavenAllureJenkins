pipeline {
    agent any

    tools {
        jdk "jdk-17"
        maven "maven 3.9.6"
    }

    parameters {
        booleanParam(defaultValue: true, description: "run regress tests", name: 'regress')
        booleanParam(defaultValue: false, description: "run smoke tests", name: 'smoke')
    }

    stages {
        stage('Build') {
            steps {
                // Забираем репо из гитхаб
                git branch: 'master', credentialsId: 'jenkins', url: 'https://github.com/Alenin556/MavenAllureJenkins.git'
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
        stage('Smoke tests') {
            when {
                expression { return params.rest}
            }
            steps {
                // Выполняем команду mvn тест
                bat "mvn -Dgroups=smoke verify test"
            }
        }
    }

    post {
        always {
            allure ([
                    reportBuildPolicy: 'ALWAYS',
                    result: [[path: 'target/allure-results']]
            ])
        }
    }
}