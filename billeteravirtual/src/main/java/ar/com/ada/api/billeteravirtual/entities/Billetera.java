package ar.com.ada.api.billeteravirtual.entities;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;

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

	public void seCargoCuenta(String moneda, BigDecimal saldo, String detalle, String conceptoOperacion) {
		for (Cuenta x : this.cuentas) {
			if (x.getMoneda().equals(moneda)) {
				Transaccion transaccion = new Transaccion();
				transaccion.crearTransaccion(saldo, x, this, detalle, conceptoOperacion);
				x.actualizarSaldo(saldo);
			}
		}
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

}