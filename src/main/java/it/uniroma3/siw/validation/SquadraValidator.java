package it.uniroma3.siw.validation;
import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Stagione;
import it.uniroma3.siw.service.SquadraService;
import it.uniroma3.siw.service.StagioneService;

@Component
public class SquadraValidator implements Validator{

@Autowired SquadraService squadraService;
	
	@Override
	public void validate(Object o, Errors errors) {
		Squadra squadra = (Squadra)o;
		Squadra esistente = squadraService.getSquadraByNome(squadra.getNome());
		if (esistente != null && esistente.getId()!=squadra.getId()) {
	errors.reject("squadra.duplicata");
		 }
		if(!(squadra.getLogoUrl().endsWith(".jpg") || squadra.getLogoUrl().endsWith(".png"))) {
			errors.rejectValue("logoUrl","logoUrl.formato");
		}
	}
	

	@Override
	public boolean supports(Class<?> aClass) {
		return Squadra.class.equals(aClass);
	}
}
