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
                echo 'Ejecutando pruebas de API con Serenity...'
                // Damos permisos de ejecución al wrapper de Gradle en el entorno de Jenkins
                sh 'chmod +x gradlew'
                // Ejecutamos la tarea de limpieza y testeo
                sh './gradlew clean test'
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