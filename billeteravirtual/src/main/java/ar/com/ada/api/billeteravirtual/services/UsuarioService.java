package ar.com.ada.api.billeteravirtual.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import ar.com.ada.api.billeteravirtual.entities.*;
import ar.com.ada.api.billeteravirtual.models.response.LoginResponse;
import ar.com.ada.api.billeteravirtual.repos.UsuarioRepository;
import ar.com.ada.api.billeteravirtual.security.Crypto;
import ar.com.ada.api.billeteravirtual.system.comm.EmailService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PersonaService personaService;

    @Autowired
    BilleteraService billeteraService;

    @Autowired
    EmailService emailService;

    private static final String BIENVENIDA = "bienvenida";

    private static final String LOGIARSE = "iniciarSesion";
    
    private static final String LOGIARSE_ERROR = "sesionInvalida";

    public Usuario buscarPorUsername(String username) {
      return usuarioRepository.findByUsername(username);
    }
    
    public void login(String username, String password) {
      Usuario u = buscarPorUsername(username);

      if (u == null || !u.getPassword().equals(Crypto.encrypt(password, u.getUsername()))) {
        emailService.alertaPorRecibirPor(u,LOGIARSE_ERROR);
        throw new BadCredentialsException("Usuario o contraseña invalida");
      }
      emailService.alertaPorRecibirPor(u,LOGIARSE);
    }
    
    public Usuario crearUsuario(String nombre,Integer paisId, Integer tipoDocumento, String documento, Date fechaNacimiento, String email, String password) {
      Persona persona = new Persona();
      persona.cargarPersona(fechaNacimiento, paisId, tipoDocumento, documento, nombre);

      Usuario usuario = new Usuario();
      usuario.cargarUsuario(email, password);
      persona.setUsuario(usuario);
      personaService.grabar(persona);

      Billetera billetera = new Billetera();
      billetera.crearCuentas();
      persona.setBilletera(billetera);

      billeteraService.grabar(billetera);
      billeteraService.cargarSaldo(billetera, new BigDecimal(500),"ARS","Bienvenido al sistemaGracias por crearte unUsuario de Regalo te damos $500","regalo");
      emailService.alertaPorRecibirPor(usuario,BIENVENIDA);
      return usuario;
    }

    public Usuario getUsuarioPorEmail(String email) {
		  return usuarioRepository.findByEmail(email);   
    }

    public Usuario getUsuarioPorId(Integer id){
      return usuarioRepository.findByUsuarioId(id);
  }

  public String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts.builder().setId("softtekJWT").setSubject(username).claim("authorities",
		grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + 600000)).signWith(SignatureAlgorithm.HS512,
		secretKey.getBytes()).compact();

		return "Bearer " + token;
	}

  public LoginResponse loginResponse(Usuario u, String token, String username) {
    LoginResponse r = new LoginResponse(); 
    r.id = u.getUsuarioId(); 
    r.billeteraId = u.getPersona().getBilletera().getBilleteraId(); 
    r.username = username; 
    r.email = u.getEmail(); 
    r.token = token; 
	  return r;
  }
}