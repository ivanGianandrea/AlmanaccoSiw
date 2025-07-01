package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import it.uniroma3.siw.model.PosizioneClassifica;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Stagione;
import it.uniroma3.siw.service.PosizioneClassificaService;
import it.uniroma3.siw.service.SquadraService;
import it.uniroma3.siw.service.StagioneService;
import it.uniroma3.siw.validation.StagioneValidator;
import jakarta.validation.Valid;

@Controller
public class StagioneController {
	@Autowired
    private StagioneService stagioneService;
	@Autowired
	private StagioneValidator stagioneValidator;
	@Autowired
	private PosizioneClassificaService posizioneClassificaService;

    // Form per creare una nuova stagione
    @GetMapping("/admin/stagione/inserisci")
    public String mostraFormNuovaStagione(Model model) {
        model.addAttribute("stagione", new Stagione());
        return "formNewStagione";
    }

    // Salva la nuova stagione
    @PostMapping("/admin/stagione/inserisci/salvata")
    public String createStagione(@Valid @ModelAttribute Stagione stagione,BindingResult bindingResult) {
    	this.stagioneValidator.validate(stagione, bindingResult);
    	if(!bindingResult.hasErrors()) {
        stagioneService.salvaStagione(stagione);
        return "gestioneStagioni";
    	}
    	return "formNewStagione";
    }

    // Visualizza tutte le stagioni
    @GetMapping("/admin/stagioni")
    public String listStagioni(Model model) {
        model.addAttribute("stagioni", stagioneService.getStagioni());
        return "elencoStagioni";
    }
    //Visualizza gestioneStagioni
    @GetMapping("/admin/gestioneStagioni")
    public String mostraGestioneStagioni() {
    	return"gestioneStagioni";
    }
    
    //-----------------
    //Visualizza tutte le stagioni per l utente
    @GetMapping("/stagioniUtente")
    public String listStagioniUtente(Model model) {
        model.addAttribute("stagioni", stagioneService.getStagioni());
        return "elencoStagioniUtente";
    }
    
    //Visualizza la pagina della singola stagione
    @GetMapping("/stagioniUtente/{idStagione}")
    public String mostraSingolaStagione(@PathVariable("idStagione") Long idStagione, Model model) {
    	Stagione stagione= stagioneService.findById(idStagione);
    	List<PosizioneClassifica> classifica = posizioneClassificaService.getClassificaPerStagione(idStagione);
    	PosizioneClassifica campione = posizioneClassificaService.getCampione(idStagione);
    	PosizioneClassifica piuGolFatti = posizioneClassificaService.getSquadraPiuGolFatti(idStagione);
    	PosizioneClassifica menoGolSubiti = posizioneClassificaService.getSquadraMenoGolSubiti(idStagione);
    	List<PosizioneClassifica> retrocesse = posizioneClassificaService.getSquadreRetrocesse(idStagione);
    	model.addAttribute("classifica", classifica);
    	model.addAttribute("stagione", stagione);
    	model.addAttribute("campione", campione);
    	model.addAttribute("piuGol", piuGolFatti);
    	model.addAttribute("menoGol", menoGolSubiti);
    	model.addAttribute("retrocesse", retrocesse);
    	return "stagione";
    }
    
    @PostMapping("/admin/eliminaStagione/{idStagione}")
    public String eliminaStagione(@PathVariable("idStagione") long idStagione) {
    	 Stagione stagione = stagioneService.findById(idStagione);
    	 for (Squadra squadra : stagione.getSquadre()) {
             squadra.getStagioni().remove(stagione);
         }
    	stagioneService.elimina(idStagione);
    	return "redirect:/admin/stagioni";
    }
}

