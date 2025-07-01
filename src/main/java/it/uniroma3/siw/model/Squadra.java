package it.uniroma3.siw.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class Squadra {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String nome;
	@NotBlank
	private String citta;
	@NotNull
	@Min(1800)
	@Max(2025)
	private Integer annoFondazione;
	private String logoUrl;
	
	@ManyToMany(mappedBy = "squadre")
	private List<Stagione> stagioni;
	
	@OneToMany(mappedBy = "squadra", cascade = CascadeType.REMOVE)
	private List<PosizioneClassifica> posizioni;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public List<Stagione> getStagioni() {
		return stagioni;
	}

	public void setStagioni(List<Stagione> stagioni) {
		this.stagioni = stagioni;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public Integer getAnnoFondazione() {
		return annoFondazione;
	}

	public void setAnnoFondazione(Integer annoFondazione) {
		this.annoFondazione = annoFondazione;
	}

	public List<PosizioneClassifica> getPosizioni() {
		return posizioni;
	}

	public void setPosizioni(List<PosizioneClassifica> posizioni) {
		this.posizioni = posizioni;
	}

}
