package ar.com.ada.api.billeteravirtual.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "transaccion")
public class Transaccion {

	@Id
	@Column(name = "transaccion_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transaccionId;

	@ManyToOne
	@JoinColumn(name = "cuenta_id", referencedColumnName = "cuenta_id")
    private Cuenta cuenta;

    private Date fecha;

	@Column(name = "estado_id")
    private Integer estadoId;

    private BigDecimal importe;

    private String moneda;

	@Column(name = "tipo_operacion")
    private TipoTransaccionEnum tipoOperacion;

	@Column(name = "concepto_operacion")
    private String conceptoOperacion;

    private String detalle;

	@Column(name = "de_usuario_id")
    private Integer deUsuarioId;

	@Column(name = "a_usuario_id")
    private Integer aUsuarioId;

	@Column(name = "de_cuenta_id")
    private Integer deCuentaId;

	@Column(name = "a_cuenta_id")
	private Integer aCuentaId;

	
	
	public Integer getTransaccionId() {
		return transaccionId;
	}

	public void setTransaccionId(Integer transaccionId) {
		this.transaccionId = transaccionId;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getEstadoId() {
		return estadoId;
	}

	public void setEstadoId(Integer estadoId) {
		this.estadoId = estadoId;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public TipoTransaccionEnum getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(TipoTransaccionEnum tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

	public String getConceptoOperacion() {
		return conceptoOperacion;
	}

	public void setConceptoOperacion(String conceptoOperacion) {
		this.conceptoOperacion = conceptoOperacion;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Integer getDeUsuarioId() {
		return deUsuarioId;
	}

	public void setDeUsuarioId(Integer deUsuarioId) {
		this.deUsuarioId = deUsuarioId;
	}

	public Integer getaUsuarioId() {
		return aUsuarioId;
	}

	public void setaUsuarioId(Integer aUsuarioId) {
		this.aUsuarioId = aUsuarioId;
	}

	public Integer getDeCuentaId() {
		return deCuentaId;
	}

	public void setDeCuentaId(Integer deCuentaId) {
		this.deCuentaId = deCuentaId;
	}

	public Integer getaCuentaId() {
		return aCuentaId;
	}

	public void setaCuentaId(Integer aCuentaId) {
		this.aCuentaId = aCuentaId;
	}

	public void crearTransaccion(BigDecimal saldo, String detalle, String conceptoOperacion, TipoTransaccionEnum tiipoOperacion) {
        this.setFecha(new Date());
		
		this.setImporte(saldo);
		
		this.setDetalle(detalle);
		
		this.setConceptoOperacion(conceptoOperacion);

		this.setTipoOperacion(tiipoOperacion);
		
		this.setEstadoId(1);
	}

	public boolean esTransacionEntrada() {
		return this.getTipoOperacion() == TipoTransaccionEnum.ENTRANTE;
	}

	 /***
     * En este caso es un ENUMERADO con numeracion default En JAVA. Estos comienzan
     * desde 0 y si intercambiamos el orden el 0 pasa a ser siempre el primero. Si
     * quisieramos tener uno customizado, en JAVA es mas complejo(se ahoga en un
     * vaso de agua)
     */
    public enum TipoTransaccionEnum {
        SALIENTE, // Este es siempre 0
        ENTRANTE // Este es siempre 1
    }

    public enum ResultadoTransaccionEnum {
        ERROR_IMPORTE_NEGATIVO, // Este es siempre 0
        INICIADA, // Este es siempre 1
        SALDO_INSUFICIENTE, BILLETERA_DESTINO_NO_ENCONTRADA, BILLETERA_ORIGEN_NO_ENCONTRADA, LIMITE_DIARIO_ALCANZADO,
        CUENTA_ORIGEN_INEXISTENTE, CUENTA_DESTINO_INEXITENTE, SE_QUIERE_PAGAR_A_SI_MISMO, EMAIL_DESTINO_INEXISTENTE
    }

    /***
     * En este caso es un ENUMERADO con numeracion customizada En JAVA, los
     * enumerados con numeros customizados deben tener un constructor y un
     * comparador para poder funcionar correctamente
     */
    // Este es un ejemplo de enumerado de estados customizados.
    public enum EstadoTransaccionEnum {
        PENDIENTE(0), ENVIADA(1), RECIBIDA(2), EJECUTADA(4), FALTA_FONDOS(80), ERROR_GENERAL(99);

        private final int value;

        // NOTE: Enum constructor tiene que estar en privado
        private EstadoTransaccionEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static EstadoTransaccionEnum parse(int id) {
            EstadoTransaccionEnum status = null; // Default
            for (EstadoTransaccionEnum item : EstadoTransaccionEnum.values()) {
                if (item.getValue() == id) {
                    status = item;
                    break;
                }
            }
            return status;
        }
    }
}