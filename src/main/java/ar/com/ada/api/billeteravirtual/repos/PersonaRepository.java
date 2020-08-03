package ar.com.ada.api.billeteravirtual.repos;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import ar.com.ada.api.billeteravirtual.entities.Billetera;
import ar.com.ada.api.billeteravirtual.entities.Persona;
import ar.com.ada.api.billeteravirtual.entities.Usuario;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    List<Persona> findByBilletera(Billetera billetera);
    
    List<Persona> findByDocumento(String documento);

    List<Persona> findByFechaNacimiento(Date fechaNacimiento);

    List<Persona> findByNombre(String nombre);

    List<Persona> findByPaisId(Integer paisId);

    List<Persona> findByPersonaId(Integer personaId);

    List<Persona> findByTipoDocumento(Integer tipoDocumento);

    List<Persona> findByUsuario(Usuario usuario);
}