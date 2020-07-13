package ar.com.ada.api.billeteravirtual.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.billeteravirtual.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    public Usuario findByUsername(String userName);
    
    public Usuario findByEmail(String email);

    public Usuario findByUsuarioId(Integer usuarioId);
}