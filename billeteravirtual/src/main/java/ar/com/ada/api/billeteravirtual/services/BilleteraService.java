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


    public void cargarSaldo(Integer billeteraId, BigDecimal saldo, String moneda,String detalle, String conceptoOperacion,Integer tipoOperacion) {
        Billetera billetera = billeteraRepository.findByBilleteraId(billeteraId);
        
        billetera.cargarCuenta(moneda,saldo, detalle, conceptoOperacion,1);

        this.grabar(billetera);
    }

    public void enviarSaldo(Integer deBilleteraId, Integer aBilleteraId, String moneda, BigDecimal saldo,String detalle,String conceptoOperacion ,Integer tipoOperacion) {
        Billetera deBilletera = this.buscarPorId(deBilleteraId);

        Billetera aBilletera = this.buscarPorId(aBilleteraId);

        deBilletera.enviarSaldo(aBilletera,moneda,saldo,detalle,conceptoOperacion,tipoOperacion);

        this.grabar(aBilletera);

        this.grabar(deBilletera);
    }
    
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