package ar.com.ada.api.billeteravirtual.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.billeteravirtual.entities.Billetera;
import ar.com.ada.api.billeteravirtual.models.response.BilleteraResponse;
import ar.com.ada.api.billeteravirtual.services.BilleteraService;

@RestController
public class BilleteraController {

    @Autowired
    BilleteraService service;

    @GetMapping("billeteras/{id}/saldos/{moneda}")
    public ResponseEntity<BilleteraResponse> consultarSaldo(@PathVariable Integer id,@PathVariable String moneda) { 
        BilleteraResponse response = new BilleteraResponse();

        response.saldo = service.consultarSaldo(id, moneda);
        
        response.moneda = moneda;

        return ResponseEntity.ok(response);
    }

    @GetMapping("billeteras/{id}/saldos")
    public ResponseEntity<List<BilleteraResponse>> consultarSaldos(@PathVariable Integer id) { 
        List<BilleteraResponse> responses = new ArrayList<>();

        responses = service.getCuentas(service.buscarPorId(id));

        return ResponseEntity.ok(responses);
    }    

    /*
    * webMetodo 1:

            consultar saldo: GET 
            URL:/

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