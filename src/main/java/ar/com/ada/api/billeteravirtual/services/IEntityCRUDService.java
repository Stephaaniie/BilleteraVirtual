package ar.com.ada.api.billeteravirtual.services;

import java.util.List;

import ar.com.ada.api.billeteravirtual.exception.ResourceNotFoundException;

public interface IEntityCRUDService <T> {
    
    List<T> findAll();

    T findById(Integer id) throws ResourceNotFoundException;

    T save(T entity);

    void delete(T entity);

    void deleteById(Integer id);

    Long count();
}