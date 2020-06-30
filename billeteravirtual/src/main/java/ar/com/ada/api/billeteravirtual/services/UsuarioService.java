package ar.com.ada.api.billeteravirtual.services;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.billeteravirtual.entities.*;
import ar.com.ada.api.billeteravirtual.repos.UsuarioRepository;
import ar.com.ada.api.billeteravirtual.security.Crypto;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PersonaService personaService;

    @Autowired
    BilleteraService billeteraService;

	public Usuario buscarPorUsername(String username) {
		return null;
	}

	public void login(String username, String password) {
	}
    
    public Usuario crearUsuario(String nombre,Integer paisId, Integer tipoDocumento, String documento, Date fechaNacimiento, String email, String password) {
        Persona persona = new Persona();
        persona = cargarPersona(fechaNacimiento, paisId, persona, tipoDocumento, documento, nombre);
        
        Usuario usuario = new Usuario();
        cargarUsuario(usuario, email, password);
        persona.setUsuario(usuario);
        personaService.grabar(persona);

        Billetera billetera = new Billetera();
        crearCuentas(billetera);
        persona.setBilletera(billetera);

        billeteraService.grabar(billetera);
        
        return usuario;
    }

    public Persona cargarPersona(Date fechaNacimiento, Integer paisId, Persona persona,Integer tipoDocumento,String documento, String nombre) {
        persona.setTipoDocumento(tipoDocumento);
        
        persona.setDocumento(documento);
        
        persona.setFechaNacimiento(fechaNacimiento);
        
        persona.setNombre(nombre);
        
        persona.setPaisId(paisId);
        
        return persona;
    }

    public Usuario cargarUsuario(Usuario usuario, String email,String password) {
        usuario.setUsername(email);
        
        usuario.setEmail(email);
        
        usuario.setPassword(Crypto.encrypt(password, email));
        
        return usuario;
    }

    public void crearCuentas(Billetera billetera) {

        Cuenta cuentaPesos = new Cuenta();
        Cuenta cuentaDolares = new Cuenta();

        cuentaPesos.setSaldo(new BigDecimal(0));
        cuentaDolares.setSaldo(new BigDecimal(0));

        cuentaPesos.setMoneda("ARS");
        cuentaDolares.setMoneda("USD");

        billetera.agregarCuenta(cuentaPesos);
        billetera.agregarCuenta(cuentaDolares);
    }
}