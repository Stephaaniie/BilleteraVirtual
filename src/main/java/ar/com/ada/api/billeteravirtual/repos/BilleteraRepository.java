package ar.com.ada.api.billeteravirtual.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.ada.api.billeteravirtual.entities.Billetera;
import ar.com.ada.api.billeteravirtual.entities.Persona;

public interface BilleteraRepository extends JpaRepository<Billetera, Integer> {
    
    List<Billetera> findByPersona(Persona persona);
    
    Billetera findByBilleteraId(Integer billeteraId);
    
}