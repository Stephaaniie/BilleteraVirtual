package ar.com.ada.api.billeteravirtual.entities;

import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
	@LazyCollection(LazyCollectionOption.FALSE)
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
		this.transacciones.add(transaccion);
		
		transaccion.setCuenta(this);

		actualizarSaldo(transaccion);
	}

	public Transaccion crearTransaccion(BigDecimal saldo, String detalle, String conceptoOperacion, Integer tipoOperacion) {
		Transaccion transaccion = new Transaccion();

		transaccion.setCuenta(this);

		transaccion.setMoneda(this.getMoneda());
		
		transaccion.crearTransaccion(saldo, detalle, conceptoOperacion, tipoOperacion);
				
		return transaccion;
	}

	public void actualizarSaldo(Transaccion transaccion) {
		this.setSaldo(transaccion.esTransacionEntrada(ENTRADA)?
		(this.getSaldo().add(transaccion.getImporte())):
		(this.getSaldo().subtract(transaccion.getImporte())));
	}

	public void actualizarUsuarios(Transaccion transaccion,Integer id) {
		transaccion.setaUsuarioId(transaccion.getTipoOperacion().equals(ENTRADA)?
		(id):this.getCuentaId());
	}

	public void crearTransaccion(BigDecimal saldo, Cuenta cSaliente, Cuenta cEntrante, Billetera eBilletera,Billetera sBilletera, String detalle, String conceptoOperacion) {
		Transaccion tEntrante = cEntrante.crearTransaccion(saldo, detalle, conceptoOperacion,1);
		
		Transaccion tSaliente = cSaliente.crearTransaccion(saldo, detalle, conceptoOperacion,0);

		tEntrante.setaCuentaId(cSaliente.getCuentaId());

		tEntrante.setaUsuarioId(sBilletera.getUsuarioId());

		tSaliente.setDeCuentaId(cSaliente.getCuentaId());

		tSaliente.setDeUsuarioId(sBilletera.getUsuarioId());

		cSaliente.agregarTransaccion(tSaliente);
		
		cEntrante.agregarTransaccion(tEntrante);
	}

	public Transaccion crearTransaccion(BigDecimal saldo, Billetera billetera, Cuenta cuenta, String detalle,String conceptoOperacion, Integer tipoOperacion) {
		
		Transaccion transaccion = cuenta.crearTransaccion(saldo, detalle, conceptoOperacion, tipoOperacion);

		transaccion.setDeCuentaId(cuenta.getCuentaId());

		transaccion.setDeUsuarioId(billetera.getUsuarioId());
		
		transaccion.setaUsuarioId(billetera.getUsuarioId());
		
		transaccion.setaCuentaId(cuenta.getCuentaId());
				
		return transaccion;
	}

}