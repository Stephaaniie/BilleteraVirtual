package ar.com.ada.api.billeteravirtual.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.billeteravirtual.entities.*;
import ar.com.ada.api.billeteravirtual.repos.BilleteraRepository;

@Service
public class BilleteraService {
    
    @Autowired
    BilleteraRepository billeteraRepository;

    public void grabar(Billetera billetera) {
        billeteraRepository.save(billetera);
    }


    public void cargarSaldo(Integer billeteraId, BigDecimal saldo, String moneda,String detalle, String conceptoOperacion) {
        Billetera billetera = billeteraRepository.findByBilleteraId(billeteraId);
        
        billetera.seCargoCuenta(moneda, saldo, detalle, conceptoOperacion);

        this.grabar(billetera);
    }

    
    /*
    1.2-- hacer transaccion 
    1.3-- actualizar el saldo de la billetera
    */


    /* 2. Metodo: enviar plata
    2.1-- recibir un importe, la moneda en la que va a estar ese importe
    recibir una billetera de origen y otra de destino
    2.2-- actualizar los saldos de las cuentas (a una se le suma y a la otra se le resta)
    2.3-- generar dos transacciones
    */

    /* 3. Metodo: consultar saldo 
    3.1-- recibir el id de la billetera y la moneda en la que esta la cuenta
    */
}