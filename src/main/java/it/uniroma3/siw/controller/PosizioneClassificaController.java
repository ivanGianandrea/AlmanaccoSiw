package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.PosizioneClassifica;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Stagione;
import it.uniroma3.siw.service.PosizioneClassificaService;
import it.uniroma3.siw.service.SquadraService;
import it.uniroma3.siw.service.StagioneService;
import it.uniroma3.siw.validation.PosizioneClassificaValidator;
import jakarta.validation.Valid;

@Controller
public class PosizioneClassificaController {
	 @Autowired
	    private PosizioneClassificaService posizioneClassificaService;

	    @Autowired
	    private SquadraService squadraService;
	    @Autowired
	    private StagioneService stagioneService;
	    @Autowired
	    private PosizioneClassificaValidator posizioneClassificaValidator;

	    
	    
	    // Form per creare una nuova posizione in classifica
	    @GetMapping("/admin/posizione/inserisci")
	    public String mostraFormNuovaPosizione(Model model) {
	        model.addAttribute("posizione", new PosizioneClassifica());
	        model.addAttribute("squadre", squadraService.getSquadre());
	        model.addAttribute("stagioni",stagioneService.getStagioni());
	        return "formNewPosizione";
	    }

	    // Salva la posizione in classifica
	    @PostMapping("/admin/posizione/inserisci/salvata")
	    public String creaNuovasPosizione(@Valid @ModelAttribute("posizione") PosizioneClassifica posizione, BindingResult bindingResult, @RequestParam("squadraId") Long squadraId,@RequestParam("stagioneId") Long stagioneId,Model model) {
	    	Squadra squadra = squadraService.findById(squadraId);
	    	Stagione stagione = stagioneService.findById(stagioneId);
	        posizione.setSquadra(squadra); 
	        posizione.setStagione(stagione);
	        if (!squadra.getStagioni().contains(stagione)) {
	            squadra.getStagioni().add(stagione);
	        }
	        if (!stagione.getSquadre().contains(squadra)) {
	            stagione.getSquadre().add(squadra);
	        }
	        posizioneClassificaValidator.validate(posizione, bindingResult);
	        if(!bindingResult.hasErrors()) {
	        squadraService.salvaSquadra(squadra);  
	        stagioneService.salvaStagione(stagione);
	        posizioneClassificaService.save(posizione);
	        return "gestioneSquadra";
	        }
	        model.addAttribute("posizione", posizione);
	        model.addAttribute("squadre", squadraService.getSquadre());
	        model.addAttribute("stagioni",stagioneService.getStagioni());
	        return "formNewPosizione";
	    }
	    

	    //Visualizza tutte le posizioni in classifica per ciascuna squadra
	    @GetMapping("/admin/posizioni/{idSquadra}")
	    public String PosizioniPerSquadra(@PathVariable("idSquadra") Long idSquadra,Model model) {
	        Squadra squadra = squadraService.findById(idSquadra);
	        List<PosizioneClassifica> posizionamenti = posizioneClassificaService.findBySquadra(squadra);
	        model.addAttribute("squadra", squadra);
	        model.addAttribute("posizionamenti", posizionamenti);
	        model.addAttribute("stagioni",stagioneService.getStagioni());
	        return "elencoPosizioniPerSquadra";
	    }
	    
	    //Visualizza la form per modificare il posizionamento di una squadra
	    @GetMapping("/admin/posizioni/{idSquadra}/{idPosizioneClassifica}/modifica")
	    public String mostraModificaPosizionePerSquadra(@PathVariable("idSquadra") Long idSquadra,@PathVariable("idPosizioneClassifica") Long idPosizioneClassifica,Model model) {
	    	 Squadra squadra = squadraService.findById(idSquadra);
	    	 PosizioneClassifica posizioneClassifica = posizioneClassificaService.findById(idPosizioneClassifica);
	    	 List<Stagione> stagioni = stagioneService.getStagioniRimanenti(posizioneClassifica.getStagione());
	    	 model.addAttribute("squadra", squadra);
	    	 model.addAttribute("posizioneClassifica", posizioneClassifica);
	    	 model.addAttribute("stagioni", stagioni);
	    	 return "formUpdatePosizioneClassifica";
	    }
	    
	    //aggiornamento PosizioneClassifica
	    @PostMapping("/admin/posizioni/{idSquadra}/{idPosizioneClassifica}/update")
	    public String aggiornaPosizionePerSquadra(@Valid @ModelAttribute("posizioneClassifica") PosizioneClassifica posizioneClassifica,BindingResult bindingResult,
	    		@PathVariable("idSquadra") Long idSquadra,@PathVariable("idPosizioneClassifica") Long idPosizioneClassifica, 
                @RequestParam("stagioneId") Long stagioneId,
                Model model) {
	    	    Squadra squadra = squadraService.findById(idSquadra);
                Stagione nuovaStagione = stagioneService.findById(stagioneId);
                posizioneClassifica.setStagione(nuovaStagione);
                posizioneClassifica.setSquadra(squadra);
   	    	 posizioneClassificaValidator.validate(posizioneClassifica, bindingResult);

                if (bindingResult.hasErrors()) {
                    model.addAttribute("squadra", squadra);
                    model.addAttribute("stagioni", stagioneService.getStagioniRimanenti(nuovaStagione));
                    return "formUpdatePosizioneClassifica";
                }

                posizioneClassificaService.save(posizioneClassifica);

                return "redirect:/admin/posizioni/" + idSquadra;
         
	    }
	    
	   
	    
	    @PostMapping("/admin/posizioni/{idSquadra}/{idPosizioneClassifica}/elimina")
	    public String eliminaPosizione(@PathVariable("idSquadra") Long idSquadra,@PathVariable("idPosizioneClassifica") Long idPosizioneClassifica,Model model) {
	    	PosizioneClassifica posizioneClassifica = posizioneClassificaService.findById(idPosizioneClassifica);
	    	posizioneClassificaService.delete(posizioneClassifica);
	    	return "redirect:/admin/posizioni/"+idSquadra;
	    }
	    
	    
}

