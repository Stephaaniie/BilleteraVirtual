package ar.com.ada.api.billeteravirtual.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.ada.api.billeteravirtual.entities.Billetera;
import ar.com.ada.api.billeteravirtual.entities.Cuenta;
import ar.com.ada.api.billeteravirtual.entities.Persona;
import ar.com.ada.api.billeteravirtual.models.response.BilleteraResponse;

public interface BilleteraRepository extends JpaRepository<Billetera, Integer> {
    
    List<Billetera> findByPersona(Persona persona);
    
    List<Billetera> findByCuentasResponse(List<BilleteraResponse> cuentasResponse);

    List<Billetera> findByCuentas(List<Cuenta> cuentas);

    List<Billetera> findByCuentaPorMoneda(Cuenta cuentaPorMoneda);

    Billetera findByBilleteraId(Integer billeteraId);
    
}