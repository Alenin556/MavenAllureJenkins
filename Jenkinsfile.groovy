
pipeline {
    agent any

    tools {
        maven "3.9.6"
    }

    stages{
        stage('Build') {
            steps {
                git "https://github.com/Alenin556/MavenAllureJenkins.git",
                        branch: 'master',

                        sh "mvn verify"
            }
        }
    }
}