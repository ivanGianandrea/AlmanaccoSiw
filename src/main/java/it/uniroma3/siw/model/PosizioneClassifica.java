package it.uniroma3.siw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
	public class PosizioneClassifica {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    @NotNull
	    private int posizione;
	    @NotNull
	    private int punti;
	    @NotNull
	    private int vittorie;
	    @NotNull
	    private int pareggi;
	    @NotNull
	    private int sconfitte;
	    @NotNull
	    private int golFatti;
	    @NotNull
	    private int golSubiti;

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

		public int getPosizione() {
			return posizione;
		}

		public void setPosizione(int posizione) {
			this.posizione = posizione;
		}

		public int getPunti() {
			return punti;
		}

		public void setPunti(int punti) {
			this.punti = punti;
		}

		public int getVittorie() {
			return vittorie;
		}

		public void setVittorie(int vittorie) {
			this.vittorie = vittorie;
		}

		public int getPareggi() {
			return pareggi;
		}

		public void setPareggi(int pareggi) {
			this.pareggi = pareggi;
		}

		public int getSconfitte() {
			return sconfitte;
		}

		public void setSconfitte(int sconfitte) {
			this.sconfitte = sconfitte;
		}

		public int getGolFatti() {
			return golFatti;
		}

		public void setGolFatti(int golFatti) {
			this.golFatti = golFatti;
		}

		public int getGolSubiti() {
			return golSubiti;
		}

		public void setGolSubiti(int golSubiti) {
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

