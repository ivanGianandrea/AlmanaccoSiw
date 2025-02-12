package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.repository.SquadraRepository;
import it.uniroma3.siw.service.SquadraService;
import it.uniroma3.siw.validation.SquadraValidator;
import jakarta.validation.Valid;

@Controller
public class SquadraController {

	@Autowired
    private SquadraService squadraService;
    @Autowired
    private SquadraValidator squadraValidator;
    
	@GetMapping("/admin/gestioneSquadra")
	public String mostraGestioneSquadra()
	{
		return "gestioneSquadra";
	}
	
    @GetMapping("/admin/squadra/inserisci")
    public String mostraFormNuovaSquadra(Model model) {
        model.addAttribute("squadra", new Squadra());
        return "formNewSquadra"; 
    }

    @PostMapping("/admin/squadra/inserisci/salvata")
    public String inserisciSquadra(@Valid @ModelAttribute Squadra squadra,BindingResult bindingResult,Model model) {
    	this.squadraValidator.validate(squadra, bindingResult);
    	if(!bindingResult.hasErrors()) {
        squadraService.salvaSquadra(squadra);
        model.addAttribute("squadre", squadraService.getSquadre());
        return "elencoSquadre";
    	}
    	return "formNewSquadra";
    }

    @GetMapping("/admin/squadre")
    public String visualizzaSquadre(Model model) {
        model.addAttribute("squadre", squadraService.getSquadre());
        return "elencoSquadre"; 
    }
    
}

