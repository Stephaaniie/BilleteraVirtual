package ar.com.ada.api.billeteravirtual.models.response;

public class TransaccionResponse {

    public TransaccionResponse(boolean b, String string) {
        this.isOk =b;
        this.menssage = string;
	}

	public boolean isOk = false;

    public String menssage = "";
    
}