package ar.com.ada.api.billeteravirtual.models.request;

import java.util.Date;

public class RegistrationRequest {
    
    public String fullName;
    
    public Integer country;

    public Integer identificationType;

    public String identification;

    public Date birthDate;

    public String email;

    public String password;
    
}