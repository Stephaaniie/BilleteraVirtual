package ar.com.ada.api.billeteravirtual.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.billeteravirtual.entities.*;
import ar.com.ada.api.billeteravirtual.repos.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PersonaService personaService;

	public Usuario buscarPorUsername(String username) {
		return null;
	}

	public void login(String username, String password) {
	}
    
    public Usuario crearUsuario(String nombre,Integer paisId, Integer tipoDocumento, String documento, Date fechaNacimiento, String email, String password){
        
        Persona persona = new Persona();
        persona.setTipoDocumento(tipoDocumento);
        persona.setDocumento(documento);
        persona.setFechaNacimiento(fechaNacimiento);
        persona.setNombre(nombre);
        persona.setPaisId(paisId);

        Usuario usuario = new Usuario();
        usuario.setUsername(email);
        usuario.setEmail(email);
        usuario.setPassword(password);

        persona.setUsuario(usuario);

        personaService.grabar(persona);
        
        return usuario;
    }
}