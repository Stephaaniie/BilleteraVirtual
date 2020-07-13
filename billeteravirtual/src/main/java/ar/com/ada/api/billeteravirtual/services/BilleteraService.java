package ar.com.ada.api.billeteravirtual.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.billeteravirtual.entities.*;
import ar.com.ada.api.billeteravirtual.entities.Transaccion.ResultadoTransaccionEnum;
import ar.com.ada.api.billeteravirtual.models.response.BilleteraResponse;
import ar.com.ada.api.billeteravirtual.repos.BilleteraRepository;
import ar.com.ada.api.billeteravirtual.services.validaciones.*;
import ar.com.ada.api.billeteravirtual.system.comm.EmailService;

@Service
public class BilleteraService {
    
    @Autowired
    BilleteraRepository billeteraRepository;

    @Autowired
    UsuarioService uService;

    @Autowired
    EmailService emailService;

    private Validation validacion = new Validation();

    private ValidationList vList = new ValidationList();
    
    private static final String ENVIO_DE_SALDO = "envio";

    private static final String RECEPTOR_DE_SALDO = "receptor";

    private static final String RECARGA = "recarga";


    public void grabar(Billetera billetera) {
        billeteraRepository.save(billetera);
    }


    public boolean cargarSaldo(Billetera billetera, BigDecimal saldo, String moneda,String detalle, String conceptoOperacion) {
        List<ResultadoTransaccionEnum> resultados = new ArrayList<>();

        resultados.add(validacion.validarBilletera(billetera));

        billetera.cargarCuenta(resultados,moneda,saldo, detalle, conceptoOperacion);

        emailService.alertaPorRecibirPor(uService.getUsuarioPorId(billetera.getUsuarioId()),RECARGA);
        
        return vList.estaTodoOk(resultados);
    }

    public boolean enviarSaldo(Integer deBilleteraId, String email, String moneda, BigDecimal saldo,String detalle,String conceptoOperacion) {
        List<ResultadoTransaccionEnum> resultados = new ArrayList<>();

        resultados.add(validacion.validarEmail(email));
        Usuario usuario =  uService.getUsuarioPorEmail(email);
        
        Billetera deBilletera = this.buscarPorId(deBilleteraId);
        resultados.add(validacion.validarBilletera(deBilletera));

        Billetera aBilletera = usuario.getBilletera();
        resultados.add(validacion.validarBilletera(aBilletera));

        deBilletera.enviarSaldo(resultados,deBilletera,aBilletera,moneda,saldo,detalle,conceptoOperacion);

        emailService.alertaPorRecibirPor(uService.getUsuarioPorId(deBilletera.getUsuarioId()),ENVIO_DE_SALDO);
        emailService.alertaPorRecibirPor(usuario,RECEPTOR_DE_SALDO);

        this.grabar(aBilletera);
        this.grabar(deBilletera);

        return vList.estaTodoOk(resultados);
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