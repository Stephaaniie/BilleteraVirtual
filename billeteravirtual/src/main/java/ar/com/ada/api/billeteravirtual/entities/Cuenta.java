package ar.com.ada.api.billeteravirtual.entities;

import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "cuenta")
public class Cuenta {

	@Id
	@Column(name = "cuenta_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cuentaId;
	
	private static final Integer ENTRADA = 1;

    private BigDecimal saldo;

    private String moneda;

	@ManyToOne
	@JoinColumn(name = "billetera_id",referencedColumnName = "billetera_id")
    private Billetera billetera;

	@OneToMany(mappedBy = "cuenta",cascade = CascadeType.ALL)
    private List<Transaccion> transacciones = new ArrayList<>();

	public Integer getCuentaId() {
		return cuentaId;
	}

	public void setCuentaId(Integer cuentaId) {
		this.cuentaId = cuentaId;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public Billetera getBilletera() {
		return billetera;
	}

	public void setBilletera(Billetera billetera) {
		this.billetera = billetera;
	}

	public List<Transaccion> getTransacciones() {
		return transacciones;
	}

	public void setTransacciones(List<Transaccion> transacciones) {
		this.transacciones = transacciones;
	}

	public void agregarTransaccion(Transaccion transaccion){
		
		if (transaccion.esTransacionEntrada(ENTRADA)) {

			this.actualizarSaldo(transaccion.getImporte());
		}else{

			this.descontarSaldo(transaccion.getImporte());
		}
		this.transacciones.add(transaccion);
		
		transaccion.setCuenta(this);
	}

	public Transaccion crearTransaccion(BigDecimal saldo,Integer id, String detalle, String conceptoOperacion, Integer tiipoOperacion) {
		Transaccion transaccion = new Transaccion();

		transaccion.setCuenta(this);

		transaccion.setMoneda(this.getMoneda());
		
		transaccion.crearTransaccion(saldo, detalle, conceptoOperacion, tiipoOperacion);
		
		if (transaccion.esTransacionEntrada(ENTRADA)) {
			transaccion.setaUsuarioId(id);

			transaccion.setaCuentaId(this.getCuentaId());
		}else{
			transaccion.setDeUsuarioId(this.getCuentaId());

			transaccion.setDeUsuarioId(id);
		}		
		return transaccion;
	}


	public void descontarSaldo(BigDecimal saldo) {
		BigDecimal saldoActual = this.getSaldo();
		
		this.setSaldo(saldoActual.subtract(saldo));
	}

	public void actualizarSaldo(BigDecimal saldo) {
		BigDecimal saldoActual = this.getSaldo();
		
		this.setSaldo(saldoActual.add(saldo));
	}
    
}