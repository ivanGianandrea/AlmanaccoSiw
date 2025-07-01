package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Stagione;
import it.uniroma3.siw.repository.StagioneRepository;

@Service
public class StagioneService {
	@Autowired
	StagioneRepository stagioneRepository;

	public Stagione salvaStagione(Stagione stagione) {
		return stagioneRepository.save(stagione);
	}

	public List<Stagione> getStagioni() {
		return (List<Stagione>) stagioneRepository.findAll();
	}

	public Stagione findById(Long stagioneId) {

		return stagioneRepository.findById(stagioneId).get();
	}

	public boolean stagioneEsistente(Integer annoInizio, Integer annoFine) {
		return stagioneRepository.existsByAnnoInizioAndAnnoFine(annoInizio, annoFine);

	}

	public List<Stagione> getStagioniRimanenti(Stagione stagione) {
		List<Stagione> stagioni = this.getStagioni();
		stagioni.remove(stagione);
		return stagioni;

	}

	public void elimina(long id) {
		stagioneRepository.deleteById(id);
	}
}
