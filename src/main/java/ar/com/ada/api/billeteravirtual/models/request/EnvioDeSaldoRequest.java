package ar.com.ada.api.billeteravirtual.models.request;

import java.math.BigDecimal;

public class EnvioDeSaldoRequest {

    public BigDecimal saldo;

    public String moneda;

    public String email;

    public String motivo;

    public String detalle;
}