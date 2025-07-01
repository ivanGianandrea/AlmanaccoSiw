package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import it.uniroma3.siw.model.PosizioneClassifica;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Stagione;

public interface PosizioneClassificaRepository extends CrudRepository<PosizioneClassifica, Long>  {

	public List<PosizioneClassifica> findBySquadra(Squadra squadra);
	
	@Query("SELECT a FROM PosizioneClassifica a WHERE a.stagione.id = :idStagione ORDER BY a.posizione ASC ")
	public List<PosizioneClassifica> findClassificaByStagione(@Param("idStagione")Long idStagione);
	public boolean existsByStagioneAndSquadra(Stagione stagione,Squadra squadra);
	
	@Query(value = "SELECT * FROM posizione_classifica WHERE stagione_id = :idStagione ORDER BY posizione ASC LIMIT 1", nativeQuery = true)
	public Optional<PosizioneClassifica> findCampioneByStagione(@Param("idStagione")Long idStagione);
	
	@Query(value = "SELECT * FROM posizione_classifica WHERE stagione_id = :idStagione ORDER BY gol_fatti DESC LIMIT 1", nativeQuery = true)
	public Optional<PosizioneClassifica> findPiuGolFattiByStagione(@Param("idStagione")Long idStagione);
	
	@Query(value = "SELECT * FROM posizione_classifica WHERE stagione_id = :idStagione ORDER BY gol_subiti ASC LIMIT 1", nativeQuery = true)
	public Optional<PosizioneClassifica> findMenoGolSubitiByStagione(@Param("idStagione")Long idStagione);
	
	@Query(value = "SELECT * FROM posizione_classifica WHERE stagione_id = :idStagione ORDER BY posizione DESC LIMIT 3", nativeQuery = true)
	public List<PosizioneClassifica> findRetrocesseByStagione(@Param("idStagione")Long idStagione);
	
	public boolean existsByStagioneAndPosizione(Stagione stagione,Integer posizione);
	public int countByStagione(Stagione stagione);
public PosizioneClassifica findByStagioneAndSquadra(Stagione stagione, Squadra squadra);
public PosizioneClassifica findByStagioneAndPosizione(Stagione stagione,Integer posizione);

}
