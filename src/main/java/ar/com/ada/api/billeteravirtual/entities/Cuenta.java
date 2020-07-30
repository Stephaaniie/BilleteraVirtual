package ar.com.ada.api.billeteravirtual.entities;

import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import ar.com.ada.api.billeteravirtual.entities.Transaccion.TipoTransaccionEnum;
import ar.com.ada.api.billeteravirtual.models.response.MovimientosResponse;

@Entity
@Table(name = "cuenta")
public class Cuenta {

	@Id
	@Column(name = "cuenta_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cuentaId;
	
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

	public Transaccion crearTransaccion(BigDecimal saldo, String detalle, String conceptoOperacion, TipoTransaccionEnum tipoOperacion) {
		Transaccion transaccion = new Transaccion();

		transaccion.setCuenta(this);

		transaccion.setMoneda(this.getMoneda());
		
		transaccion.crearTransaccion(saldo, detalle, conceptoOperacion, tipoOperacion);
				
		return transaccion;
	}

	public void actualizarSaldo(Transaccion transaccion) {
		this.setSaldo(transaccion.esTransacionEntrada()?
		(this.getSaldo().add(transaccion.getImporte())):
		(this.getSaldo().subtract(transaccion.getImporte())));
	}

	public void crearTransaccion(BigDecimal saldo, Cuenta cSaliente, Cuenta cEntrante, Billetera eBilletera,Billetera sBilletera, String detalle, String conceptoOperacion) {
		Transaccion tEntrante = cEntrante.crearTransaccion(saldo, detalle, conceptoOperacion,TipoTransaccionEnum.ENTRANTE);
		
		Transaccion tSaliente = cSaliente.crearTransaccion(saldo, detalle, conceptoOperacion,TipoTransaccionEnum.SALIENTE);

		tEntrante.setaCuentaId(cEntrante.getCuentaId());
		tEntrante.setaUsuarioId(eBilletera.getUsuarioId());

		tEntrante.setDeCuentaId(cSaliente.getCuentaId());
		tEntrante.setDeUsuarioId(eBilletera.getUsuarioId());

		tSaliente.setDeCuentaId(cSaliente.getCuentaId());
		tSaliente.setDeUsuarioId(sBilletera.getUsuarioId());

		tSaliente.setaCuentaId(cEntrante.getCuentaId());
		tSaliente.setaUsuarioId(eBilletera.getUsuarioId());

		cSaliente.agregarTransaccion(tSaliente);
		cEntrante.agregarTransaccion(tEntrante);
	}

	public Transaccion crearTransaccion(BigDecimal saldo, Billetera billetera, Cuenta cuenta, String detalle,String conceptoOperacion, TipoTransaccionEnum tipoOperacion) {
		
		Transaccion transaccion = cuenta.crearTransaccion(saldo, detalle, conceptoOperacion, tipoOperacion);

		transaccion.setDeCuentaId(cuenta.getCuentaId());

		transaccion.setDeUsuarioId(billetera.getUsuarioId());
		
		transaccion.setaUsuarioId(billetera.getUsuarioId());
		
		transaccion.setaCuentaId(cuenta.getCuentaId());
				
		return transaccion;
	}

	public List<MovimientosResponse> cargarMovimientos(Billetera billetera, List<Transaccion> transacciones,List<MovimientosResponse> res) {
		for (Transaccion transaccion : transacciones) {
			MovimientosResponse movimiento = new MovimientosResponse();	
			movimiento.numeroDeTransaccion = transaccion.getTransaccionId();
			movimiento.fecha = transaccion.getFecha();
			movimiento.importe = transaccion.getImporte();
			movimiento.moneda = transaccion.getMoneda();
			movimiento.conceptoOperacion = transaccion.getConceptoOperacion();
			movimiento.tipoOperacion = transaccion.getTipoOperacion();
			movimiento.detalle = transaccion.getDetalle();	
			movimiento.aUsuario = billetera.getPersona().getUsuario().getEmail();
            res.add(movimiento);
        }
		return res;
	}

}