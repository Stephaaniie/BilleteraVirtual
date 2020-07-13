package ar.com.ada.api.billeteravirtual.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.billeteravirtual.entities.*;
import ar.com.ada.api.billeteravirtual.models.request.*;
import ar.com.ada.api.billeteravirtual.models.response.*;
import ar.com.ada.api.billeteravirtual.services.*;

@RestController
public class BilleteraController {

    @Autowired
    BilleteraService service;

    @Autowired
    UsuarioService uService;

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

    @PostMapping("billeteras/{id}/recargas")
    public ResponseEntity<TransaccionResponse> cargarSaldos(@PathVariable Integer id, @RequestBody CargasRequest recarga) { 
        TransaccionResponse response = new TransaccionResponse();

        response.isOk = service.cargarSaldo(service.buscarPorId(id), recarga.importe ,recarga.moneda,recarga.detalle, recarga.motivo);
        
        response.menssage = "La recarga se realizo exitosamente";

        return ResponseEntity.ok(response);
    }

    @PostMapping("billeteras/{id}/envios")
    public ResponseEntity<TransaccionResponse> enviarSaldo(@PathVariable Integer id,@RequestBody EnvioDeSaldoRequest envio) { 
        TransaccionResponse response = new TransaccionResponse();

        response.isOk = service.enviarSaldo(id, envio.email, envio.moneda, envio.saldo, envio.detalle, envio.motivo);
        
        response.menssage = "El envio se realizo exitosamente";

        return ResponseEntity.ok(response);
    }

    @GetMapping("/billeteras/{id}/movimientos/{moneda}")
    public ResponseEntity<List<MovimientosResponse>> consultarMovimientos(@PathVariable Integer id, @PathVariable String moneda) {
        Billetera billetera = service.buscarPorId(id);
        
        List<MovimientosResponse> res = service.listarTransacciones(billetera, moneda);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/billeteras/{id}/movimientos")
    public ResponseEntity<List<MovimientosResponse>> consultarMovimientos(@PathVariable Integer id) {
		Billetera billetera = service.buscarPorId(id);
        
        List<MovimientosResponse> res = service.listarTransacciones(billetera);

        return ResponseEntity.ok(res);
    }

}