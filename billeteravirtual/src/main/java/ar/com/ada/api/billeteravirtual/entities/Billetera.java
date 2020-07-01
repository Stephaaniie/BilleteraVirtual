package ar.com.ada.api.billeteravirtual.entities;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;

import ar.com.ada.api.billeteravirtual.models.response.BilleteraResponse;

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

	public void cargarCuenta(String moneda,BigDecimal saldo, String detalle, String conceptoOperacion,Integer tiipoOperacion) {
		Cuenta cuenta = getCuentaPorMoneda(moneda);

		if (cuenta != null) {
			Transaccion transaccion = cuenta.crearTransaccion(saldo,this.getUsuarioId(), detalle, conceptoOperacion, tiipoOperacion);
		
			cuenta.agregarTransaccion(transaccion);
		}	
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
			BilleteraResponse response = new BilleteraResponse();

			response.saldo = x.getSaldo();
			
			response.moneda = x.getMoneda();
			
			responses.add(response);
		}
		return responses;
	}

	public void enviarSaldo(Billetera aBilletera,String moneda,BigDecimal saldo, String detalle, String conceptoOperacion,Integer tiipoOperacion) {
		Cuenta cSaliente = this.getCuentaPorMoneda(moneda);

		Cuenta cEntrante = this.getCuentaPorMoneda(moneda);

		Transaccion tSaliente = cSaliente.crearTransaccion(saldo, this.getUsuarioId(), detalle, conceptoOperacion, tiipoOperacion);
		tSaliente.setaCuentaId(cEntrante.getCuentaId());
		tSaliente.setaUsuarioId(aBilletera.getUsuarioId());

		Transaccion tEntrante = cEntrante.crearTransaccion(saldo, aBilletera.getUsuarioId(), detalle, conceptoOperacion, tiipoOperacion);
		tEntrante.setDeCuentaId(cSaliente.getCuentaId());
		tEntrante.setDeUsuarioId(this.getUsuarioId());

		cSaliente.agregarTransaccion(tSaliente);
		cEntrante.agregarTransaccion(tEntrante);
	}

}