# 🚀 Reto Jenkins: Automatización de API REST con Serenity BDD

Este repositorio contiene la solución al reto de automatización de pruebas para servicios REST utilizando el patrón de diseño **Screenplay**, integrado con un pipeline de **Jenkins** para la ejecución continua.

---

## 📋 Descripción del Reto
El objetivo principal es realizar la automatización de una petición GET paramétrica empleando Serenity BDD y Screenplay, consumiendo el servicio de ReqRes para listar usuarios de la página 2 y validando la consistencia de los datos:

*   **Base URL:** `https://reqres.in/`
*   **Endpoint:** `/api/users`
*   **Query Parameter:** `page=2`
*   **Aserciones:** 
    *   Código de respuesta HTTP esperado: `200`
    *   Validación de consistencia en el cuerpo de la respuesta (`page` y `total_pages`)

---

## 📂 Arquitectura del Proyecto (Patrón Screenplay)

El proyecto está estructurado bajo las mejores prácticas de automatización utilizando el patrón **Screenplay**, dividiendo las responsabilidades de la siguiente manera:

### 1. 🏃‍♂️ Runner (`RetoApiRunner.java`)
Es la clase encargada de orquestar y ejecutar la prueba empleando JUnit 5.
*   **Responsabilidad:** Configura el escenario, inicializa al actor ("Analista QA"), le asigna la habilidad de consumir servicios REST (`CallAnApi`) y define la secuencia lógica del caso (`Given / When / Then`).

### 2. 🎯 Tasks (`ConsultarUsuarios.java`)
Representa las acciones de alto nivel que el actor puede realizar en el sistema.
*   **Responsabilidad:** Construye y ejecuta de manera paramétrica la petición HTTP GET hacia el endpoint `/api/users?page=2`, configurando cabeceras y parámetros necesarios.

### 3. ❓ Questions (`ValidarListaUsuarios.java`)
Clase utilizada para interrogar el estado del sistema tras la ejecución de la tarea.
*   **Responsabilidad:** Extrae los campos específicos de la respuesta JSON (código de estado, número de página y total de páginas) para ser evaluados en las aserciones del Runner.

---

## 🛠️ Tecnologías y Herramientas Utilizadas

*   **Lenguaje:** Java 17
*   **Gestor de Dependencias:** Gradle 8.5
*   **Framework de Pruebas:** Serenity BDD (Screenplay Pattern) con JUnit 5
*   **Integración Continua:** Jenkins Pipeline
*   **Librerías de Aserción:** Hamcrest

---

## ⚙️ Integración Continua (Jenkinsfile)

El flujo de integración continua está definido de manera declarativa en el archivo `Jenkinsfile`. Para garantizar la compatibilidad entre Gradle y el entorno de ejecución, el pipeline fuerza el uso del JDK de Java 17 configurado en el sistema local:

```groovy
stage('Clean & Test') {
    steps {
        echo 'Ejecutando pruebas de API con Serenity usando Java 17...'
        withEnv(['JAVA_HOME=/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home']) {
            sh 'chmod +x gradlew'
            sh './gradlew clean test'
        }
    }
}