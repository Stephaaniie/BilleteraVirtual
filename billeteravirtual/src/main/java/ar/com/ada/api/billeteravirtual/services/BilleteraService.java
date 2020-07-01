package ar.com.ada.api.billeteravirtual.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.billeteravirtual.entities.*;
import ar.com.ada.api.billeteravirtual.models.response.BilleteraResponse;
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
        
        billetera.cargarCuenta(moneda, saldo, detalle, conceptoOperacion);

        this.grabar(billetera);
    }

    public void enviarSaldo(Integer deBilleteraId, Integer aBilleteraId, String Moneda, Integer importe) {
        Billetera deBilletera = billeteraRepository.findByBilleteraId(deBilleteraId);

        Billetera aBilletera = billeteraRepository.findByBilleteraId(aBilleteraId);


    }
    /* 2. Metodo: enviar plata
    2.1-- recibir un importe, la moneda en la que va a estar ese importe
    recibir una billetera de origen y otra de destino
    2.2-- actualizar los saldos de las cuentas (a una se le suma y a la otra se le resta)
    2.3-- generar dos transacciones
    */
    public BigDecimal consultarSaldo(Integer billeteraId, String moneda) {
        Billetera billetera = billeteraRepository.findByBilleteraId(billeteraId);

        return billetera.getCuentaPorMoneda(moneda).getSaldo();
    }

    public Billetera buscarPorId(Integer id) {
        return billeteraRepository.findByBilleteraId(id);
    }


	public List<BilleteraResponse> getCuentas(Billetera billetera) {
        return billetera.getCuentasResponse();
	}
}