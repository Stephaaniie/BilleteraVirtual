# BilleteraVirtual

WEB API. El proyecto de Spring Boot que se Deployo en Heroku. La BilleteraVirtual es un sistema de transferencia de dinero electrónico con el que se pueden hacer múltiples operaciones financieras(enviar saldo, consultar saldo, cargar saldo, etc.). 

# Para el almacenamiento se genero de dos maneras.

1) Se utilizo inicalmente como base de datos MYSQL. 

2) Al deployar con Heroku se utilizó Postgre para base de datos.

Se utilizó Postman para probar la WEB API. Se utilizó el servicio de MAILGUN para poder notificar con mails transaccionales los movimientos realizados en la billetera. Se realizaron Tests Unitarios y se utilizó JWT Token para seguridad. Se trabajaron ramas y se probaron Pull Requests. 

Se utiliza:
# MySQL como DB. 
Como muestra la imagen, asi quedan relacionadas las tablas:

<div style="width: 100%">
 <img width="49.15%" src="https://github.com/Stephaaniie/BilleteraVirtual/blob/master/billeteravirtual/src/main/resources/img/diagramaDeBaseDato.png"/>
</div>

# Postman para probar la WEB API.
Algunas imagenes de como se relaciono la interaccion.

<div style="width: 100%">
 <img width="49.15%" src="https://github.com/Stephaaniie/BilleteraVirtual/blob/master/billeteravirtual/src/main/resources/img/Postman.png"/>
</div>

# Mailgun para el envio de mails.
Ejemplo de como llegaron las alertas al email autorizado desde Mailgun.

<div style="width: 100%">
 <img width="49.15%" src="https://github.com/Stephaaniie/BilleteraVirtual/blob/master/billeteravirtual/src/main/resources/img/EMAIL.png"/>
</div>

# Heroku
Se deployó con Heroku con Postgre para base de datos.
<a href="https://scbilleteravirtual.herokuapp.com/" target="_blank">API scbilleteravirtual<a>

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/c2ab73f1ac504f91add3852976faa073)](https://www.codacy.com/manual/Stephaaniie/BilleteraVirtual?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Stephaaniie/BilleteraVirtual&amp;utm_campaign=Badge_Grade)

