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

    @Autowired
    UsuarioService uService;

    public void grabar(Billetera billetera) {
        billeteraRepository.save(billetera);
    }


    public void cargarSaldo(Billetera billetera, BigDecimal saldo, String moneda,String detalle, String conceptoOperacion) {
        
        billetera.cargarCuenta(moneda,saldo, detalle, conceptoOperacion);

        this.grabar(billetera);
    }

    public void enviarSaldo(Integer deBilleteraId, String email, String moneda, BigDecimal saldo,String detalle,String conceptoOperacion) {
        Billetera deBilletera = this.buscarPorId(deBilleteraId);

        Usuario usuario =  uService.getUsuarioPorEmail(email);

        Billetera aBilletera = usuario.getBilletera();

        deBilletera.enviarSaldo(deBilletera,aBilletera,moneda,saldo,detalle,conceptoOperacion);

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