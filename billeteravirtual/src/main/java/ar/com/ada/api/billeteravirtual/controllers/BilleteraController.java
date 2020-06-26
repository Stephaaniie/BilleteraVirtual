package ar.com.ada.api.billeteravirtual.controllers;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class BilleteraController {

    /*
    * webMetodo 1:

            consultar saldo: GET 
            URL:/billeteras/{id}/saldos

      webMetodo 2:

            cargar saldo: POST
            URL:/billeteras/{id}/recargas
            requestBody: 
            {
                "moneda":
                "importe":
            }

        webMetodo 3:
            
            enviar saldo: POST
            URL:/billetera/{id}/envios
            requestBody:
            {
                "moneda":
                "importe":
                "email":
                "motivo":
                "detalleDelMotivo":
            }


    */
}