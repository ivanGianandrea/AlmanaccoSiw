package it.uniroma3.siw.model;


import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Stagione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Min(value = 1898)
    private Integer annoInizio;
    @NotNull
    @Min(value = 1899)
    private Integer annoFine;

    @OneToMany(mappedBy = "stagione", cascade = CascadeType.REMOVE)
    private List<PosizioneClassifica> classifica;

    @ManyToMany
    @JoinTable(
        name = "stagione_squadra",
        joinColumns = @JoinColumn(name = "stagione_id"),
        inverseJoinColumns = @JoinColumn(name = "squadra_id"))
         private List<Squadra> squadre;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAnnoInizio() {
		return annoInizio;
	}

	public void setAnnoInizio(Integer annoInizio) {
		this.annoInizio = annoInizio;
	}

	public Integer getAnnoFine() {
		return annoFine;
	}

	public void setAnnoFine(Integer annoFine) {
		this.annoFine = annoFine;
	}

	public List<PosizioneClassifica> getClassifica() {
		return classifica;
	}

	public void setClassifica(List<PosizioneClassifica> classifica) {
		this.classifica = classifica;
	}

	public List<Squadra> getSquadre() {
		return squadre;
	}

	public void setSquadre(List<Squadra> squadre) {
		this.squadre = squadre;
	}

	@Override
	public String toString() {
		return annoInizio+"/"+annoFine;
	}
    
  
}