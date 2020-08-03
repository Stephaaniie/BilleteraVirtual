package ar.com.ada.api.billeteravirtual.repos;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.billeteravirtual.entities.Persona;
import ar.com.ada.api.billeteravirtual.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    Usuario findByUsuarioId(Integer usuarioId);

    Usuario findByUsername(String username);
   
    Usuario findByPersona(Persona persona);

    Usuario findByPassword(String password);

    List<Usuario> findByFechaLogin(Date fechaLogin);

    Usuario findByEmail(String email);
}