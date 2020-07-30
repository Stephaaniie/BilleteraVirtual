package ar.com.ada.api.billeteravirtual.entities;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;

import ar.com.ada.api.billeteravirtual.entities.Transaccion.ResultadoTransaccionEnum;
import ar.com.ada.api.billeteravirtual.entities.Transaccion.TipoTransaccionEnum;
import ar.com.ada.api.billeteravirtual.models.response.BilleteraResponse;
import ar.com.ada.api.billeteravirtual.services.validaciones.Validation;
import ar.com.ada.api.billeteravirtual.services.validaciones.ValidationList;

@Entity
@Table(name = "billetera")
public class Billetera {
    
    @Id
    @Column(name = "billetera_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer billeteraId;

    @OneToOne
    @JoinColumn(name = "persona_id",referencedColumnName = "persona_id")
    private Persona persona;

    @OneToMany(mappedBy = "billetera",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<Cuenta> cuentas = new ArrayList<>();
	
	@Transient
	private Validation validacion = new Validation();

	@Transient
	private ValidationList vList = new ValidationList();

	public Integer getBilleteraId() {
		return billeteraId;
	}

	public void setBilleteraId(Integer billeteraId) {
		this.billeteraId = billeteraId;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}   

	public void agregarCuenta(Cuenta cuenta){
		this.cuentas.add(cuenta);
		
		cuenta.setBilletera(this);
	}

	public void cargarCuenta(List<ResultadoTransaccionEnum> resultados,String moneda,BigDecimal saldo, String detalle, String conceptoOperacion) {
		Cuenta cuenta = getCuentaPorMoneda(moneda);
		resultados.add(validacion.validarCuenta(cuenta));
		resultados.add(validacion.validarCuentaSaliente(cuenta, saldo));
		resultados.add(validacion.validarImporte(saldo));

		Transaccion transaccion = cuenta.crearTransaccion(saldo,this,cuenta, detalle, conceptoOperacion, TipoTransaccionEnum.ENTRANTE);
		
		cuenta.agregarTransaccion(transaccion);
	}

	public Integer getUsuarioId() {
		return this.persona.getUsuarioId();
	}

	public Cuenta getCuentaPorMoneda(String moneda){
		Cuenta cuenta = null;
		for (Cuenta x : this.cuentas) {
			if (x.getMoneda().equals(moneda)) {
				cuenta = x;
			}
		}
		return cuenta;
	}

	public void crearCuentas() {
        Cuenta cuentaPesos = new Cuenta();
        Cuenta cuentaDolares = new Cuenta();

        cuentaPesos.setSaldo(new BigDecimal(0));
        cuentaDolares.setSaldo(new BigDecimal(0));

        cuentaPesos.setMoneda("ARS");
        cuentaDolares.setMoneda("USD");

        this.agregarCuenta(cuentaPesos);
        this.agregarCuenta(cuentaDolares);
    }

	public List<BilleteraResponse> getCuentasResponse() {
		List<BilleteraResponse> responses = new ArrayList<>();

		for (Cuenta x : this.cuentas) {
			BilleteraResponse response = new BilleteraResponse(x.getSaldo(), x.getMoneda());
			
			responses.add(response);
		}
		return responses;
	}

	public void enviarSaldo(List<ResultadoTransaccionEnum> resultados, Billetera deBilletera, Billetera aBilletera,String moneda,BigDecimal saldo, String detalle, String conceptoOperacion) {
		Cuenta cSaliente = deBilletera.getCuentaPorMoneda(moneda);
		resultados.add(validacion.validarCuenta(cSaliente));
		resultados.add(validacion.validarCuentaSaliente(cSaliente, saldo));
		resultados.add(validacion.validarImporte(saldo));

		Cuenta cEntrante = aBilletera.getCuentaPorMoneda(moneda);
		resultados.add(validacion.validarCuenta(cEntrante));

		cSaliente.crearTransaccion(saldo,cSaliente,cEntrante,aBilletera, this, detalle, conceptoOperacion);
	}

}