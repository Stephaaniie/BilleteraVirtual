package ar.com.ada.api.billeteravirtual.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.com.ada.api.billeteravirtual.entities.Persona;
import ar.com.ada.api.billeteravirtual.exception.ResourceNotFoundException;
import ar.com.ada.api.billeteravirtual.repos.PersonaRepository;
import ar.com.ada.api.billeteravirtual.services.IPersonaService;

@Service
public class PersonaService implements IPersonaService{

	private final PersonaRepository personaRepository;

	public PersonaService(PersonaRepository personaRepository){
		this.personaRepository = personaRepository;
	}

	@Override
	public List<Persona> findAll() {
		return personaRepository.findAll();
	}

	@Override
	public Persona save(Persona persona) {
		return personaRepository.save(persona);
	}

	@Override
	public Long count() {
		return personaRepository.count();
    }

	@Override
	public Persona findById(Integer id) throws ResourceNotFoundException {
		return personaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("model with id " + id + " not found"));
	}

	@Override
	public void delete(Persona persona) {
		this.deleteById(persona.getUsuarioId());
		
	}

	@Override
	public void deleteById(Integer id) {
		if (!personaRepository.existsById(id)){
      		throw new ResourceNotFoundException("model with id " + id + " not found");
    	}
    	personaRepository.deleteById(id);	
	}
}