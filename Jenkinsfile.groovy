#!groovy

node {
    stage('Build') {
        echo 'Build'
    }
    stage('Test') {
        echo 'Tests'
    }
    stage('Allure') {
        echo 'Allure Report'
    }
}
