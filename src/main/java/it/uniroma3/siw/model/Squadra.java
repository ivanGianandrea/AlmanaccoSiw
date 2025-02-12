package it.uniroma3.siw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

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
	public Long getId() {
		return id;
	}
	 @ManyToMany(mappedBy = "squadre")
	    private List<Stagione> stagioni;
	 
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
    
}
