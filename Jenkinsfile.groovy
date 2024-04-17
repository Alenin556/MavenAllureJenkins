#!groovy

node {
    stage('Build') {
        echo 'Build started'
        step {
            git url : "https://github.com/Alenin556/MavenAllureJenkins.git"
            branch: 'master'

            sh "mvn verify"
        }
    }
    stage('Test') {
        echo 'Tests'
    }
    stage('Allure') {
        echo 'Allure Report'
    }
}
