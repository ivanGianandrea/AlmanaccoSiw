package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Stagione;

public interface StagioneRepository extends CrudRepository<Stagione, Long> {

	public Boolean existsByAnnoInizioAndAnnoFine(Integer annoInizio, Integer annoFine);
	
	
}
