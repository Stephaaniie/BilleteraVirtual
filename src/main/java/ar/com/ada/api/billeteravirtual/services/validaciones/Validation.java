package ar.com.ada.api.billeteravirtual.services.validaciones;

import java.math.BigDecimal;

import ar.com.ada.api.billeteravirtual.entities.Billetera;
import ar.com.ada.api.billeteravirtual.entities.Cuenta;
import ar.com.ada.api.billeteravirtual.entities.Transaccion.ResultadoTransaccionEnum;

public class Validation {

    public ResultadoTransaccionEnum validarEmail(String email){
        if (email == null)
            return ResultadoTransaccionEnum.EMAIL_DESTINO_INEXISTENTE;
        return ResultadoTransaccionEnum.EMAIL_EXISTENTE;
    }

    public ResultadoTransaccionEnum validarBilletera(Billetera billetera){
        if (billetera == null)
            return ResultadoTransaccionEnum.BILLETERA_NO_ENCONTRADA;
        return ResultadoTransaccionEnum.BILLETERA_ENCONTRADA;
    }

    public ResultadoTransaccionEnum validarCuenta(Cuenta cuenta){
        if (cuenta == null)
            return ResultadoTransaccionEnum.CUENTA_INEXISTENTE;
        return ResultadoTransaccionEnum.CUENTA_EXITENTE;
    }

    public ResultadoTransaccionEnum validarCuentaSaliente(Cuenta cuenta, BigDecimal importe){
        if (cuenta.getSaldo().compareTo(importe) == -1)
            return ResultadoTransaccionEnum.SALDO_INSUFICIENTE;
        return ResultadoTransaccionEnum.SALDO_APROBADO;
    }
    
    public ResultadoTransaccionEnum validarImporte(BigDecimal importe) {
        if (importe.compareTo(new BigDecimal(0)) == -1)
            return ResultadoTransaccionEnum.ERROR_IMPORTE_NEGATIVO;
        return ResultadoTransaccionEnum.INICIADA;
    }

}