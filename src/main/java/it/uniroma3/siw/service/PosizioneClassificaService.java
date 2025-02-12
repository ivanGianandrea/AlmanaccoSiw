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
		return this.posizioneClassificaRepository.findCampioneByStagione(idStagione);
	}
    
    public PosizioneClassifica getSquadraMenoGolSubiti(Long idStagione) {
		return this.posizioneClassificaRepository.findMenoGolSubitiByStagione(idStagione);
	}
    
    public PosizioneClassifica getSquadraPiuGolFatti(Long idStagione) {
		return this.posizioneClassificaRepository.findPiuGolFattiByStagione(idStagione);
	}
    
    public List<PosizioneClassifica> getSquadreRetrocesse(Long idStagione){
    	return this.posizioneClassificaRepository.findRetrocesseByStagione(idStagione);
    }
    //se esiste già nella classifica quella posizione
    public boolean esistePosizioneNellaClassifica(Stagione stagione,int posizione) {
    	return this.posizioneClassificaRepository.existsByStagioneAndPosizione(stagione, posizione);
    }
	
}
