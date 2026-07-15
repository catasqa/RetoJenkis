pipeline {
    agent any

    tools {
        // Debe coincidir exactamente con el nombre que configuraste en el Paso 1
        jdk 'JDK17' 
    }

    stages {
        stage('Checkout') {
            steps {
                // Descarga el código desde tu GitHub automáticamente
                checkout scm
            }
        }
        
        stage('Clean & Test') {
            steps {
                echo 'Ejecutando pruebas de API con Serenity usando Java 17...'
                // Forzamos a Jenkins a usar tu JDK 17 de Temurin
                withEnv(['JAVA_HOME=/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home']) {
                    sh 'chmod +x gradlew'
                    sh './gradlew clean test'
                }
            }
        }

        stage('Generate Reports') {
            steps {
                echo 'Generando reportes finales...'
                // Agrega el reporte estructurado de Serenity
                sh './gradlew aggregate'
            }
        }
    }

    post {
        always {
            // Guarda y expone los reportes HTML de Serenity en la interfaz de Jenkins
            archiveArtifacts artifacts: 'target/site/serenity/**/*', onlyIfSuccessful: false
        }
    }
}

