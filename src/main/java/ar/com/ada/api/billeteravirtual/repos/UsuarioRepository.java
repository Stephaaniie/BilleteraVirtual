package ar.com.ada.api.billeteravirtual.repos;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.billeteravirtual.entities.Billetera;
import ar.com.ada.api.billeteravirtual.entities.Persona;
import ar.com.ada.api.billeteravirtual.entities.Usuario;
import ar.com.ada.api.billeteravirtual.models.response.LoginResponse;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    Usuario findByUsuarioId(Integer usuarioId);

    Usuario findByUsername(String username);
   
    Usuario findByPersona(Persona persona);

    Usuario findByPassword(String password);

    List<Usuario> findByFechaLogin(Date fechaLogin);

    Usuario findByEmail(String email);

    List<Usuario> findByBilletera(Billetera billetera);

    Usuario login(String username, String password);

	LoginResponse loginResponse(Usuario u, String token, String username);
}