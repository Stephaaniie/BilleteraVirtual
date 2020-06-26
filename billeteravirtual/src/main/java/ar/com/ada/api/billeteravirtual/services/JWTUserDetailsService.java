package ar.com.ada.api.billeteravirtual.services;

import org.springframework.stereotype.Service;

import ar.com.ada.api.billeteravirtual.entities.Usuario;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;

@Service
public class JWTUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioService.buscarPorUsername(username);

        if (usuario != null) {
            return new User(usuario.getUsername(), usuario.getPassword(), new ArrayList<>());
        }else{
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}