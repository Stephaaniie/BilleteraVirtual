# BilleteraVirtual  :purse: :dollar: :octocat:

WEB API. El proyecto de Spring Boot que se Deployo en Heroku. La BilleteraVirtual es un sistema de transferencia de dinero electrónico con el que se pueden hacer múltiples operaciones financieras(enviar saldo, consultar saldo, cargar saldo, etc.). 

* **Para el almacenamiento se genero de dos maneras.** 

    ```
        1) Se utilizo inicalmente como base de datos MYSQL. 

        2) Al deployar con Heroku se utilizó Postgre para base de datos.

        Se utilizó Postman para probar la WEB API. Se utilizó el servicio de MAILGUN para poder notificar con mails transaccionales los movimientos realizados en la billetera. Se realizaron Tests Unitarios y se utilizó JWT Token para seguridad. Se trabajaron ramas y se probaron Pull Requests.
    ```
 

Se utiliza:
### MySQL como DB. :open_file_folder:
Como muestra la imagen, asi quedan relacionadas las tablas:

<div style="width: 100%">
 <img width="49.15%" src="https://github.com/Stephaaniie/BilleteraVirtual/blob/master/src/main/resources/img/diagramaDeBaseDato.png"/>
</div>

### POSTMAN :shipit:
Para la solicitud de los WebMetodos.
Algunas imagenes de como se relaciono la interaccion.

<div style="width: 100%">
 <img width="49.15%" src="https://github.com/Stephaaniie/BilleteraVirtual/blob/master/src/main/resources/img/Postman.png"/>
</div>

A continuacion del mismo se adjuntara el acceso a los request de Postman.

<a href="https://documenter.getpostman.com/view/12223320/T1DtfbSd?version=latest" target="_blank">ACCESO REQUEST POSTMAN<a>

### MAILGUM :mailbox: :love_letter:
Para poder notificar con mails el login del usuario o los intentos fallidos, ademas de notificar cada transacción que realiza el usuario.
Ejemplo de como llegaron las alertas al email autorizado desde Mailgun.

<div style="width: 100%">
 <img width="49.15%" src="https://github.com/Stephaaniie/BilleteraVirtual/blob/master/src/main/resources/img/EMAIL.png"/>
</div>

### HEROKU :computer:
Se deployó con Heroku con Postgre para base de datos.
<a href="https://billetera-virtual-sc.herokuapp.com/" target="_blank">API billetera-virtual-sc<a>

### Pre-requisitos 📋

* **Tener instalado**
    ```
        JAVA 11
        MAVEN 3.6.0
    ```

### Instalación 🔧
     El proyecto tiene integrada la carpeta de .mnv por lo cual se instalara automaticamente Maven.
     Para la ejecucion del proyecto debera ejecutar:
        - mvn clean (limpiar el target).
        - mvn verify (Verifica que todas las dependencias esten instaladas correctamente).
        - mvn compile (Compila el proyecto).

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/347448a36dc446868baf39c9a37852b5)](https://www.codacy.com/manual/Stephaaniie/BilleteraVirtual?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Stephaaniie/BilleteraVirtual&amp;utm_campaign=Badge_Grade)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.codacy/codacy-coverage-reporter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.codacy/codacy-coverage-reporter)

