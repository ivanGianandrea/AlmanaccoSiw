package it.uniroma3.siw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
	public class PosizioneClassifica {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    @NotNull
	    private Integer  posizione;
	    @NotNull
	    private Integer  punti;
	    @NotNull
	    @Min(0)
	    private Integer  vittorie;
	    @NotNull
	    @Min(0)
	    private Integer  pareggi;
	    @NotNull
	    @Min(0)
	    private Integer  sconfitte;
	    @NotNull
	    @Min(0)
	    private Integer  golFatti;
	    @NotNull
	    @Min(0)
	    private Integer  golSubiti;

	    @ManyToOne
	    @JoinColumn(name = "squadra_id")
	    private Squadra squadra;

	    @ManyToOne
	    @JoinColumn(name = "stagione_id")
	    private Stagione stagione;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Integer getPosizione() {
			return posizione;
		}

		public void setPosizione(Integer  posizione) {
			this.posizione = posizione;
		}

		public Integer getPunti() {
			return punti;
		}

		public void setPunti(Integer  punti) {
			this.punti = punti;
		}

		public Integer getVittorie() {
			return vittorie;
		}

		public void setVittorie(Integer  vittorie) {
			this.vittorie = vittorie;
		}

		public Integer getPareggi() {
			return pareggi;
		}

		public void setPareggi(Integer pareggi) {
			this.pareggi = pareggi;
		}

		public Integer getSconfitte() {
			return sconfitte;
		}

		public void setSconfitte(Integer sconfitte) {
			this.sconfitte = sconfitte;
		}

		public Integer getGolFatti() {
			return golFatti;
		}

		public void setGolFatti(Integer golFatti) {
			this.golFatti = golFatti;
		}

		public Integer getGolSubiti() {
			return golSubiti;
		}

		public void setGolSubiti(Integer golSubiti) {
			this.golSubiti = golSubiti;
		}

		public Squadra getSquadra() {
			return squadra;
		}

		public void setSquadra(Squadra squadra) {
			this.squadra = squadra;
		}

		public Stagione getStagione() {
			return stagione;
		}

		public void setStagione(Stagione stagione) {
			this.stagione = stagione;
		}

	}

