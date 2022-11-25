pipeline {
    agent {
        label 'docker'
    }
    stages {
        stage('Source') {
            steps {
                git 'https://github.com/srayuso/unir-cicd.git'
            }
        }
        stage('Build') {
            steps {
                echo 'Building stage!'
                sh 'make build'
            }
        }
        stage('Unit tests') {
            steps {
                sh 'make test-unit'
                archiveArtifacts artifacts: 'results/*.xml'
            }
        }
        stage('Unit API') {
            steps {
                sh 'make test-api'
                archiveArtifacts artifacts: 'results/*.xml'
            }
        }
        stage('Unit Test e2e') {
            steps {
                sh 'make test-e2e'
                archiveArtifacts artifacts: 'results/*.xml'
            }
        }
    }
    post {
        always {
            junit 'results/*_result.xml'
            echo 'ENVIO DE CORREO ELECTRONICO'
            // emailext body: 'Numero de Ejecucion: (${BUILD_NUMBER}) - Nombre del Trabajo: '${JOB_NAME}'', subject: 'Correo Electronico', to: 'pedro@pedro.com'
            echo "Numero de Ejecucion: (${BUILD_NUMBER}) - Nombre del Trabajo: '${JOB_NAME}'"
            echo "Job '${JOB_NAME}' (${BUILD_NUMBER}) is waiting for input"
            cleanWs()
        }
    }
}
