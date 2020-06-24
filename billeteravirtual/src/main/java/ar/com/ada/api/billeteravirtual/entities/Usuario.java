package ar.com.ada.api.billeteravirtual.entities;

import java.util.Date;

public class Usuario {

    private Integer usuarioId;

    private String username;

    private String possword;

    private String email;

    private Date fechaLoing;

    private Persona persona;

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPossword() {
		return possword;
	}

	public void setPossword(String possword) {
		this.possword = possword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaLoing() {
		return fechaLoing;
	}

	public void setFechaLoing(Date fechaLoing) {
		this.fechaLoing = fechaLoing;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

    
    
}