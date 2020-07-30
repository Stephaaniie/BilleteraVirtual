package ar.com.ada.api.billeteravirtual.services.validaciones;

import java.util.List;

import ar.com.ada.api.billeteravirtual.entities.Transaccion.ResultadoTransaccionEnum;

public class ValidationList {

    public boolean todoOk;

    public final int ERROR_IMPORTE_NEGATIVO = 0;
    
    public final int SALDO_INSUFICIENTE = -1;

    public final int BILLETERA_NO_ENCONTRADA = -5;
    
	public final int LIMITE_DIARIO_ALCANZADO = -30;
    
    public final int CUENTA_INEXISTENTE = -10;

	public final int EMAIL_DESTINO_INEXISTENTE = -20;

    public boolean estaTodoOk(List<ResultadoTransaccionEnum> resultados){
        boolean respuesta = true;
        
        for (ResultadoTransaccionEnum x : resultados) {
            if ((x.getValue() == ERROR_IMPORTE_NEGATIVO) || (x.getValue() == SALDO_INSUFICIENTE)
                || (x.getValue() == BILLETERA_NO_ENCONTRADA) || (x.getValue() == LIMITE_DIARIO_ALCANZADO)
                || (x.getValue() == CUENTA_INEXISTENTE) || (x.getValue() == EMAIL_DESTINO_INEXISTENTE)) {
                respuesta = false;
            }
        }
        return respuesta;
    }
    
}