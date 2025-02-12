package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.repository.SquadraRepository;
import java.util.List;
@Service
public class SquadraService {
	
@Autowired
SquadraRepository squadraRepository;
	public List<Squadra> getSquadre(){
		return (List<Squadra>) squadraRepository.findAll();
	}
	
	public Squadra salvaSquadra(Squadra squadra) {
		return squadraRepository.save(squadra);
	}

	public Squadra findById(Long squadraId) {
		return squadraRepository.findById(squadraId).get();
	}
	public boolean esistenzaSquadra(String nome) {
		return squadraRepository.existsByNome(nome);
	}
}
