package ar.com.ada.api.billeteravirtual.entities;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "persona")
public class Persona {
	
	@Id
    @Column(name = "persona_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer personaId;

    private String nombre;

    private Integer paisId;

    private Integer tipoDocumento;
    
    private String documento;

    private Date fechaNacimiento;

    private Usuario usuario;

	@OneToOne(mappedBy = "persona",cascade = CascadeType.ALL)
    private Billetera billetera;

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Billetera getBilletera() {
		return billetera;
	}

	public void setBilletera(Billetera billetera) {
		this.billetera = billetera;
		this.billetera.setPersona(this);
	}

	public Integer getPaisId() {
		return paisId;
	}

	public void setPaisId(Integer paisId) {
		this.paisId = paisId;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Integer getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(Integer tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
}