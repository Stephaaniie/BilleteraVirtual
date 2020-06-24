package ar.com.ada.api.billeteravirtual.entities;

import java.util.*;

public class Billetera {
    
    private Integer billeteraId;

    private Persona persona;

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
}