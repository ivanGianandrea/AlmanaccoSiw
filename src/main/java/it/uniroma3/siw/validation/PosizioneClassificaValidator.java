package it.uniroma3.siw.validation;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import org.springframework.validation.Validator;

import it.uniroma3.siw.model.PosizioneClassifica;
import it.uniroma3.siw.service.PosizioneClassificaService;

@Component
public class PosizioneClassificaValidator implements Validator {

	@Autowired
	PosizioneClassificaService posizioneClassificaService;
	
	@Override
	public void validate(Object o, Errors errors) {
		PosizioneClassifica posizioneClassifica = (PosizioneClassifica)o;
		if (posizioneClassificaService.posizioneEsistente(posizioneClassifica.getStagione(), posizioneClassifica.getSquadra())) {
	errors.reject("posizioneClassifica.duplicata");
		 }
		if(posizioneClassificaService.esistePosizioneNellaClassifica(posizioneClassifica.getStagione(), posizioneClassifica.getPosizione())){
			errors.reject("posizioneClassifica.posizione.esistente");
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return PosizioneClassifica.class.equals(aClass);
	}
}
