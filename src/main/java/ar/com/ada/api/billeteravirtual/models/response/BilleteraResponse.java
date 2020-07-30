package ar.com.ada.api.billeteravirtual.models.response;

import java.math.BigDecimal;

public class BilleteraResponse {

	public String moneda;

    public BigDecimal saldo;

    public BilleteraResponse(BigDecimal saldo, String moneda) {
        this.moneda = moneda;

        this.saldo = saldo;
	}
    
}