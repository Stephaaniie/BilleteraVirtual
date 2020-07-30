package ar.com.ada.api.billeteravirtual.system.comm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ar.com.ada.api.billeteravirtual.entities.Usuario;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;

@Service
public class EmailService {

    enum TipoEnvio {
        SMTP, API
    }

    private static final String LOGIARSE = "iniciarSesion";
    
    private static final String LOGIARSE_ERROR = "sesionInvalida";

    private static final String ENVIO_DE_SALDO = "envio";
    
    private static final String BIENVENIDA = "bienvenida";

    private static final String RECEPTOR_DE_SALDO = "receptor";

    private static final String RECARGA = "recarga";

    @Value("${emailSettings.apiKey}")
    private String apiKey;

    @Value("${emailSettings.apiBaseUri}")
    public String apiBaseUri;

    @Value("${emailSettings.apiBaseUri}")
    public String requestUri;

    @Value("${emailSettings.from}")
    public String from;

    @Value("${emailSettings.domain}")
    public String domain;

    @Value("${emailSettings.enabled}")
    public boolean enabled;


    public void SendEmail(String email, String subject, String message) throws UnirestException {
        if (!this.enabled)
            return;
        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + this.domain + "/messages")
            .basicAuth("api", this.apiKey)
            .field("from", this.from)
            .field("to", email)
            .field("subject", subject)
            .field("text", message).asJson();

        request.getBody();
    }

    public void alertaPorRecibirPor(Usuario usuario,String envio){
        switch (envio) {
            case BIENVENIDA:
                this.SendEmail(usuario.getEmail(), "Bienvenida a Billetera Vitual", "Estimad@: "+ usuario.getPersona().getNombre() + "\n Te regalamos $500 por confiar en nosotros \n ¡Saludos!");
                break;
            case LOGIARSE:
                this.SendEmail(usuario.getEmail(), "Te logeaste a Billetera Virtual", usuario.getPersona().getNombre() + "\n Te damos la bienvenida a nuetro sistema. \n ¡Saludos!");
                break;
            case LOGIARSE_ERROR:
                this.SendEmail(usuario.getEmail(), "Eror ocurrio un error al intentar logearse",  usuario.getPersona().getNombre() + "\n¡Saludos!");
                break;
            case ENVIO_DE_SALDO:
                this.SendEmail(usuario.getEmail(), "Enviaste saldo", usuario.getPersona().getNombre()+"Usted realizo el envio con exito. \n¡Saludos!");
                break;
            case RECEPTOR_DE_SALDO:
                this.SendEmail(usuario.getEmail(), "Recibiste una transferencia", "Se te cargo saldo a tu cuenta con exito. \n¡Saludos!");                
                break;
            case RECARGA:
                this.SendEmail(usuario.getEmail(), "Se realizo una recarga", usuario.getPersona().getNombre()+"Se te cargo saldo a tu cuenta con exito");
                break;
            default:
                break;
        }
    }
}

