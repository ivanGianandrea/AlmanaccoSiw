package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Squadra;
import jakarta.websocket.server.PathParam;

public interface SquadraRepository extends CrudRepository<Squadra, Long> {
public boolean existsByNome(String nome);

@Query("select count(p) from PosizioneClassifica p where p.posizione = 1 and p.squadra.id = :idSquadra")
public int findCampionatiVinti(@Param("idSquadra") long idSquadra);

public Squadra findSquadraByNome(String nome);
}
