package ar.com.ada.api.billeteravirtual.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.billeteravirtual.entities.Usuario;
import ar.com.ada.api.billeteravirtual.models.request.LoginRequest;
import ar.com.ada.api.billeteravirtual.security.jwt.JWTTokenUtil;
import ar.com.ada.api.billeteravirtual.services.implementations.UsuarioService;

@RestController
@RequestMapping("auth/")
public class AuthController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<Usuario> postRegisterUser(@RequestBody Usuario usuario) {
        usuario.cargarUsuario(usuario.getUsername(), usuario.getPassword());
        return new ResponseEntity<>(usuarioService.save(usuario), HttpStatus.CREATED);
    }

    @PostMapping("/login") 
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) throws Exception {
        Usuario usuarioLogueado = usuarioService.login(authenticationRequest.username, authenticationRequest.password);

        UserDetails userDetails = usuarioService.getUserAsUserDetail(usuarioLogueado);
        
        Map<String,Object> claims = usuarioService.getUserClaims(usuarioLogueado);

        String token = jwtTokenUtil.generateToken(userDetails, claims);

        Usuario u = usuarioService.findByName(authenticationRequest.username); 
               
        return ResponseEntity.ok(usuarioService.loginResponse(u,token,authenticationRequest.username)); 
    }

    @DeleteMapping("/deleter/{id}")
    public String delete(@PathVariable("id") Integer id) {
        usuarioService.deleteById(id);
        return "OK";
    }
}