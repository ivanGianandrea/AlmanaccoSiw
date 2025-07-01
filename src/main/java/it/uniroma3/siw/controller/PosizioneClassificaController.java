package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	        squadra.getStagioni().add(stagione);
	        stagione.getSquadre().add(squadra);
	        posizioneClassificaValidator.validate(posizione, bindingResult);
	        if(!bindingResult.hasErrors()) {
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
	    @PostMapping("/admin/posizioni/{idSquadra}/{idPosizioneClassifica}/modifica/{campo}")
	    public String aggiornaPosizionePerSquadra(@PathVariable("idSquadra") Long idSquadra,@PathVariable("idPosizioneClassifica") Long idPosizioneClassifica, @PathVariable("campo") String campo, @RequestParam("valore") Integer valore, Model model) {
	        PosizioneClassifica posizioneClassifica = posizioneClassificaService.findById(idPosizioneClassifica);
	        Squadra squadra = squadraService.findById(idSquadra);
	        switch (campo) {
	            case "posizione":
	                posizioneClassifica.setPosizione(valore);
	                break;
	            case "punti":
	                posizioneClassifica.setPunti(valore);
	                break;
	            case "vittorie":
	                posizioneClassifica.setVittorie(valore);
	                break;
	            case "pareggi":
	                posizioneClassifica.setPareggi(valore);
	                break;
	            case "sconfitte":
	                posizioneClassifica.setSconfitte(valore);
	                break;
	            case "golFatti":
	                posizioneClassifica.setGolFatti(valore);
	                break;
	            case "golSubiti":
	                posizioneClassifica.setGolSubiti(valore);
	                break;
	            default:
	                return "error";  // Se il campo non è valido
	        }

	        posizioneClassificaService.save(posizioneClassifica);
	        model.addAttribute("squadra", squadra);
	    	 model.addAttribute("posizioneClassifica", posizioneClassifica);
            model.addAttribute("stagioni", stagioneService.getStagioniRimanenti(posizioneClassifica.getStagione()) );
	        return "formUpdatePosizioneClassifica"; 
	    }
	    
	    //aggiornamento della stagione della PosizioneClassifica
	    @PostMapping("/admin/posizioni/{idSquadra}/{idPosizioneClassifica}/modifica/stagione")
	    public String aggiornaPosizionePerSquadra(@PathVariable("idSquadra") Long idSquadra,@PathVariable("idPosizioneClassifica") Long idPosizioneClassifica, @RequestParam Long stagioneId, Model model) {
	        PosizioneClassifica posizioneClassifica = posizioneClassificaService.findById(idPosizioneClassifica);
	        Squadra squadra = squadraService.findById(idSquadra);
	            Stagione stagione = stagioneService.findById(stagioneId);
	                posizioneClassifica.setStagione(stagione);

	        posizioneClassificaService.save(posizioneClassifica);
	        model.addAttribute("squadra", squadra);
	    	 model.addAttribute("posizioneClassifica", posizioneClassifica);
            model.addAttribute("stagioni", stagioneService.getStagioniRimanenti(posizioneClassifica.getStagione()) );
	        return "formUpdatePosizioneClassifica"; 
	}
	    @PostMapping("/admin/posizioni/{idSquadra}/{idPosizioneClassifica}/elimina")
	    public String eliminaPosizione(@PathVariable("idSquadra") Long idSquadra,@PathVariable("idPosizioneClassifica") Long idPosizioneClassifica,Model model) {
	    	PosizioneClassifica posizioneClassifica = posizioneClassificaService.findById(idPosizioneClassifica);
	    	posizioneClassificaService.delete(posizioneClassifica);
	    	return "redirect:/admin/posizioni/"+idSquadra;
	    }
}

