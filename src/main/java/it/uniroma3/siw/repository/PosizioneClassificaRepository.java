package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

import it.uniroma3.siw.model.PosizioneClassifica;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Stagione;

public interface PosizioneClassificaRepository extends CrudRepository<PosizioneClassifica, Long>  {

	public List<PosizioneClassifica> findBySquadra(Squadra squadra);
	
	@Query("SELECT a FROM PosizioneClassifica a WHERE a.stagione.id = :idStagione ORDER BY a.posizione ASC ")
	public List<PosizioneClassifica> findClassificaByStagione(Long idStagione);
	public boolean existsByStagioneAndSquadra(Stagione stagione,Squadra squadra);
	@Query("SELECT a FROM PosizioneClassifica a WHERE a.stagione.id = :idStagione ORDER BY a.posizione ASC LIMIT 1")
	public PosizioneClassifica findCampioneByStagione(Long idStagione);
	@Query("SELECT a FROM PosizioneClassifica a WHERE a.stagione.id = :idStagione ORDER BY a.golFatti DESC LIMIT 1")
	public PosizioneClassifica findPiuGolFattiByStagione(Long idStagione);
	@Query("SELECT a FROM PosizioneClassifica a WHERE a.stagione.id = :idStagione ORDER BY a.golSubiti ASC LIMIT 1")
	public PosizioneClassifica findMenoGolSubitiByStagione(Long idStagione);
	@Query("SELECT a FROM PosizioneClassifica a WHERE a.stagione.id = :idStagione ORDER BY a.posizione DESC LIMIT 3")
	public List<PosizioneClassifica> findRetrocesseByStagione(Long idStagione);
	public boolean existsByStagioneAndPosizione(Stagione stagione,int posizione);
}
