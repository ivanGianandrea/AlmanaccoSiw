package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.stereotype.Service;
import java.util.List;
import it.uniroma3.siw.model.PosizioneClassifica;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Stagione;
import it.uniroma3.siw.repository.PosizioneClassificaRepository;
import it.uniroma3.siw.repository.SquadraRepository;

@Service
public class PosizioneClassificaService {
@Autowired
PosizioneClassificaRepository posizioneClassificaRepository;
@Autowired
SquadraService squadraService;
@Autowired
StagioneService stagioneService;

	public PosizioneClassifica save(PosizioneClassifica posizione) {
		return posizioneClassificaRepository.save(posizione);
	}
	public List<PosizioneClassifica> getPosizioni() {
		return (List<PosizioneClassifica>)posizioneClassificaRepository.findAll();
	}
	public List<PosizioneClassifica> findBySquadra(Squadra squadra){
		return posizioneClassificaRepository.findBySquadra(squadra);
		
	}
	public PosizioneClassifica findById(Long id) {
	return posizioneClassificaRepository.findById(id).get();
	}
	
	public List<PosizioneClassifica> getClassificaPerStagione(Long idStagione){
		return posizioneClassificaRepository.findClassificaByStagione(idStagione);
	}
	
	//mi verifica se esiste già nella classifica la squadra che voglio inserire
	public boolean posizioneEsistente(Stagione stagione, Squadra squadra) {
		return this.posizioneClassificaRepository.existsByStagioneAndSquadra(stagione, squadra);
	}
	
	public PosizioneClassifica getCampione(Long idStagione) {
		return this.posizioneClassificaRepository.findCampioneByStagione(idStagione).orElse(null);
	}
    
    public PosizioneClassifica getSquadraMenoGolSubiti(Long idStagione) {
		return this.posizioneClassificaRepository.findMenoGolSubitiByStagione(idStagione).orElse(null);
	}
    
    public PosizioneClassifica getSquadraPiuGolFatti(Long idStagione) {
		return this.posizioneClassificaRepository.findPiuGolFattiByStagione(idStagione).orElse(null);
	}
    
    public List<PosizioneClassifica> getSquadreRetrocesse(Long idStagione){
    	return this.posizioneClassificaRepository.findRetrocesseByStagione(idStagione);
    }
    
    //se esiste già nella classifica quella posizione
    public boolean esistePosizioneNellaClassifica(Stagione stagione,Integer posizione) {
    	return this.posizioneClassificaRepository.existsByStagioneAndPosizione(stagione, posizione);
    }
    
	public void delete(PosizioneClassifica posizioneClassifica) {
		Squadra squadra = posizioneClassifica.getSquadra();
		squadra.getPosizioni().remove(posizioneClassifica);
		Stagione stagione = posizioneClassifica.getStagione();
    	stagione.getClassifica().remove(posizioneClassifica);
    	stagioneService.salvaStagione(stagione);
    	squadraService.salvaSquadra(squadra);
		posizioneClassificaRepository.delete(posizioneClassifica);
	}
	public int partiteGiocate(Stagione stagione) {
		int numeroSquadre = posizioneClassificaRepository.countByStagione(stagione);
		return (numeroSquadre-1)*2;
	}
}
