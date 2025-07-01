package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.repository.SquadraRepository;
import it.uniroma3.siw.service.SquadraService;
import it.uniroma3.siw.validation.SquadraValidator;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SquadraController {

	@Autowired
	private SquadraService squadraService;
	@Autowired
	private SquadraValidator squadraValidator;

	@GetMapping("/admin/gestioneSquadra")
	public String mostraGestioneSquadra() {
		return "gestioneSquadra";
	}

	@GetMapping("/admin/squadra/inserisci")
	public String mostraFormNuovaSquadra(Model model) {
		model.addAttribute("squadra", new Squadra());
		return "formNewSquadra";
	}

	@PostMapping("/admin/squadra/inserisci/salvata")
	public String inserisciSquadra(@Valid @ModelAttribute Squadra squadra, BindingResult bindingResult, Model model) {
		this.squadraValidator.validate(squadra, bindingResult);
		if (!bindingResult.hasErrors()) {
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

	@GetMapping("/squadra/{idSquadra}")
	public String mostraSquadra(@PathVariable("idSquadra") long idSquadra, Model model) {
		Squadra squadra = squadraService.findById(idSquadra);
		int titoli = squadraService.getCampionatiVinti(idSquadra);
		model.addAttribute("squadra", squadra);
		model.addAttribute("titoli", titoli);
		return "squadra";
	}

	@GetMapping("/admin/mostraFormUpdateSquadra")
	public String mostraFormAggiornamento(@RequestParam("nome") String nomeSquadra, Model model) {
		Squadra squadra = squadraService.getSquadraByNome(nomeSquadra);
		if (squadra == null) {
			model.addAttribute("squadre", squadraService.getSquadre());
			model.addAttribute("message", "Non è stata trovata alcuna Squadra con questo nome");
			return "elencoSquadre";
		} else {
			model.addAttribute("squadra", squadra);
			return "formUpdateSquadra";
		}
	}

	@PostMapping("/admin/formUpdateSquadra")
	public String aggiornamento(@Valid @ModelAttribute Squadra squadra, BindingResult result, Model model) {
		squadraValidator.validate(squadra, result);
		if (!result.hasErrors()) {
			Squadra esistente = squadraService.findById(squadra.getId());
			squadra.setPosizioni(esistente.getPosizioni());
			squadra.setStagioni(esistente.getStagioni());
			squadraService.salvaSquadra(squadra);
			return "squadra";
		}
		return "formUpdateSquadra";
	}

	@PostMapping("/admin/eliminaSquadra/{idSquadra}")
	public String eliminaSquadra(@PathVariable("idSquadra") long idSquadra) {
		squadraService.eliminaSquadra(idSquadra);
		return "redirect:/admin/squadre";
	}
}
