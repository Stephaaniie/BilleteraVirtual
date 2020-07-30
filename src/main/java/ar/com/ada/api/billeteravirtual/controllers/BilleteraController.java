package ar.com.ada.api.billeteravirtual.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.billeteravirtual.entities.Billetera;
import ar.com.ada.api.billeteravirtual.entities.Usuario;
import ar.com.ada.api.billeteravirtual.models.request.CargasRequest;
import ar.com.ada.api.billeteravirtual.models.request.EnvioDeSaldoRequest;
import ar.com.ada.api.billeteravirtual.models.response.BilleteraResponse;
import ar.com.ada.api.billeteravirtual.models.response.MovimientosResponse;
import ar.com.ada.api.billeteravirtual.models.response.TransaccionResponse;
import ar.com.ada.api.billeteravirtual.services.implementations.BilleteraService;
import ar.com.ada.api.billeteravirtual.services.implementations.PersonaService;
import ar.com.ada.api.billeteravirtual.services.implementations.UsuarioService;

@RestController
@RequestMapping("billeteras/")
public class BilleteraController {

    @Autowired
    BilleteraService bService;

    @Autowired
    UsuarioService uService;

    @Autowired
    PersonaService pService;

    @GetMapping("{id}/saldos/{moneda}")
    public ResponseEntity<BilleteraResponse> consultarSaldo(@PathVariable Integer id,@PathVariable String moneda) { 
        BilleteraResponse response = bService.consultarSaldo(id, moneda);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("{id}/saldos")
    public List<BilleteraResponse> findBySaldos(@PathVariable Integer id) { 
        return bService.findAllCuentas(bService.findById(id));
    }    

    @PostMapping("{id}/recargas")
    public ResponseEntity<TransaccionResponse> cargarSaldos(@PathVariable Integer id, @RequestBody CargasRequest recarga) { 
        TransaccionResponse response = bService.cargarSaldo(bService.findById(id), recarga.importe ,recarga.moneda,recarga.detalle, recarga.motivo);
        return ResponseEntity.ok(response);
    }

    @PostMapping("{id}/envios")
    public ResponseEntity<TransaccionResponse> enviarSaldo(@PathVariable Integer id,@RequestBody EnvioDeSaldoRequest envio) { 
        TransaccionResponse response = bService.enviarSaldo(id, envio.email, envio.moneda, envio.saldo, envio.detalle, envio.motivo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}/movimientos/{moneda}")
    public List<MovimientosResponse> consultarMovimientos(@PathVariable Integer id, @PathVariable String moneda) {         
        return bService.listarTransacciones(bService.findById(id), moneda);
    }

    @GetMapping("{id}/movimientos")
    public List<MovimientosResponse> consultarMovimientos(@PathVariable Integer id) {
        return bService.listarTransacciones(bService.findById(id));
    }

    @GetMapping()
    public List<Billetera> findAllBilleteras(){
        return bService.findAll();
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Integer id) {
        bService.deleteById(id);
        return "OK";
    }

    @DeleteMapping("{id}/usuarios")
    public String deletePersona(@PathVariable Integer id) {
        pService.delete(bService.findById(id).getPersona());
        return "OK";
    }

    @PutMapping("{id}/usuarios/{id1}")
    public Usuario modificarUsuario(@PathVariable Integer id,@RequestBody Usuario usuario,Integer id1){
        bService.findById(id).getPersona().getUsuario().setUsuarioId(id1);
        return uService.save(usuario);
    }
}