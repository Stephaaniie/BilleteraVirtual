package ar.com.ada.api.billeteravirtual.services.implementations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.billeteravirtual.entities.Billetera;
import ar.com.ada.api.billeteravirtual.entities.Cuenta;
import ar.com.ada.api.billeteravirtual.entities.Transaccion;
import ar.com.ada.api.billeteravirtual.entities.Usuario;
import ar.com.ada.api.billeteravirtual.entities.Transaccion.ResultadoTransaccionEnum;
import ar.com.ada.api.billeteravirtual.exception.ResourceNotFoundException;
import ar.com.ada.api.billeteravirtual.models.response.BilleteraResponse;
import ar.com.ada.api.billeteravirtual.models.response.MovimientosResponse;
import ar.com.ada.api.billeteravirtual.models.response.TransaccionResponse;
import ar.com.ada.api.billeteravirtual.repos.BilleteraRepository;
import ar.com.ada.api.billeteravirtual.services.IBilleteraService;
import ar.com.ada.api.billeteravirtual.services.validaciones.Validation;
import ar.com.ada.api.billeteravirtual.services.validaciones.ValidationList;
import ar.com.ada.api.billeteravirtual.system.comm.EmailService;

@Service
public class BilleteraService implements IBilleteraService {
      
    @Autowired
    EmailService emailService;

    @Autowired
    UsuarioService uService;
    
    private final BilleteraRepository billeteraRepository;

    public BilleteraService(BilleteraRepository billeteraRepository){
        this.billeteraRepository = billeteraRepository;
    }

    private Validation validacion = new Validation();

    private ValidationList vList = new ValidationList();
    
    private static final String ENVIO_DE_SALDO = "envio";

    private static final String RECEPTOR_DE_SALDO = "receptor";

    private static final String RECARGA = "recarga";

    public TransaccionResponse cargarSaldo(Billetera billetera, BigDecimal saldo, String moneda,String detalle, String conceptoOperacion) {
        List<ResultadoTransaccionEnum> resultados = new ArrayList<>();

        resultados.add(validacion.validarBilletera(billetera));

        billetera.cargarCuenta(resultados,moneda,saldo, detalle, conceptoOperacion);

        emailService.alertaPorRecibirPor(uService.getUsuarioPorId(billetera.getUsuarioId()),RECARGA);
        
        return ((vList.estaTodoOk(resultados))?(new TransaccionResponse(true,"La recarga se realizo exitosamente")) :(new TransaccionResponse(false,"La recarga no se realizo"))); 
    }

    public TransaccionResponse enviarSaldo(Integer deBilleteraId, String email, String moneda, BigDecimal saldo,String detalle,String conceptoOperacion) {
        List<ResultadoTransaccionEnum> resultados = new ArrayList<>();

        resultados.add(validacion.validarEmail(email));
        Usuario usuario =  uService.findByEmail(email);
        
        Billetera deBilletera = this.findById(deBilleteraId);
        resultados.add(validacion.validarBilletera(deBilletera));

        Billetera aBilletera = usuario.getBilletera();
        resultados.add(validacion.validarBilletera(aBilletera));

        deBilletera.enviarSaldo(resultados,deBilletera,aBilletera,moneda,saldo,detalle,conceptoOperacion);

        emailService.alertaPorRecibirPor(uService.getUsuarioPorId(deBilletera.getUsuarioId()),ENVIO_DE_SALDO);
        emailService.alertaPorRecibirPor(usuario,RECEPTOR_DE_SALDO);

        this.save(aBilletera);
        this.save(deBilletera);

        return ((vList.estaTodoOk(resultados))?(new TransaccionResponse(true,"El envio se realizo exitosamente")) :(new TransaccionResponse(false,"El envio no se realizo"))); 
    }
    
    public BilleteraResponse consultarSaldo(Integer billeteraId, String moneda) {
        Billetera billetera = billeteraRepository.findByBilleteraId(billeteraId);

        return new BilleteraResponse(billetera.getCuentaPorMoneda(moneda).getSaldo(),moneda);
    }

	public List<BilleteraResponse> findAllCuentas(Billetera billetera) {
        return billetera.getCuentasResponse();
    }

	public List<MovimientosResponse> listarTransacciones(Billetera billetera, String moneda) {
        Cuenta cuenta = billetera.getCuentaPorMoneda(moneda);

        List<Transaccion> transacciones = cuenta.getTransacciones();

        List<MovimientosResponse> response = cuenta.cargarMovimientos(billetera,transacciones,new ArrayList<>());

		return response;
	}

	public List<MovimientosResponse> listarTransacciones(Billetera billetera) {
        List<Cuenta> cuentas = billetera.getCuentas();

        List<MovimientosResponse> response = new ArrayList<>();
        
        for (Cuenta cuenta : cuentas) {
            List<Transaccion> transacciones = cuenta.getTransacciones();

            response.addAll(cuenta.cargarMovimientos(billetera,transacciones, response));
        }
		return response;
	}


	@Override
	public List<Billetera> findAll() {
		return billeteraRepository.findAll();
	}

	@Override
	public Billetera findById(Integer id) throws ResourceNotFoundException {
		return billeteraRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("model with id " + id + " not found"));
	}


	@Override
	public Billetera save(Billetera billetera) {
		return billeteraRepository.save(billetera);
	}

	@Override
	public void deleteById(Integer id) {
		if (!billeteraRepository.existsById(id)){
            throw new ResourceNotFoundException("model with id " + id + " not found");
        }
        billeteraRepository.deleteById(id);	
	}

	@Override
	public Long count() {
		return billeteraRepository.count();
	}

	@Override
	public void delete(Billetera billetera) {
		this.deleteById(billetera.getUsuarioId());	
	}
}