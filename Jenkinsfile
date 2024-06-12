pipeline {
    agent any

    stages {
        stage('Build Backend') {
            steps {
                dir('apps/backend') {
                    script {
                        // Build the backend
                        sh './mvnw clean install'
                    }
                }
            }
        }
        stage('Build Frontend') {
            steps {
                dir('apps/frontend') {
                    script {
                        // Build the frontend
                        sh 'npm install'
                        sh 'npm run build'
                    }
                }
            }
        }
        stage('Test Backend') {
            steps {
                dir('apps/backend') {
                    script {
                        // EExecute backend tests
                        sh './mvnw test'
                    }
                }
            }
        }
        stage('Test Frontend') {
            steps {
                dir('apps/frontend') {
                    script {
                        // Execute frontend tests
                        sh 'npm test'
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    // Deploy the application
                    echo 'Deploying application...'
                }
            }
        }
    }
}
