package it.uniroma3.siw.validation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Stagione;
import it.uniroma3.siw.service.StagioneService;


	
@Component
public class StagioneValidator implements Validator {
	@Autowired StagioneService stagioneService;
	
	@Override
	public void validate(Object o, Errors errors) {
		Stagione stagione = (Stagione)o;
		if ( stagioneService.stagioneEsistente(stagione.getAnnoInizio(), stagione.getAnnoFine())) {
	errors.reject("stagione.duplicata");
		 }	
		if (stagione.getAnnoFine() <= stagione.getAnnoInizio()) {
		    errors.reject("stagione.annoFine.invalido");
		}
	}
	

	@Override
	public boolean supports(Class<?> aClass) {
		return Stagione.class.equals(aClass);
	}
		
		
	

}
