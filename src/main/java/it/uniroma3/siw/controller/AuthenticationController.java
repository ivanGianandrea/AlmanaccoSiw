package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.SquadraService;
import it.uniroma3.siw.service.UserService;
import it.uniroma3.siw.validation.CredentialsValidation;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {
	@Autowired
	SquadraService squadraService;
	@Autowired
	CredentialsService credentialsService;
	@Autowired
	UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
@Autowired
private CredentialsValidation credentialValidator;
//mostra la pagina di login
	@GetMapping("/login")
	public String mostraPaginaLogin() {
		
		return "login";
	}

	// mostra la pagina di registrazione
	@GetMapping("/register")
	public String mostraPaginaRegistrazione(Model model) {
		Credentials credentials = new Credentials();
		User user = new User();
		credentials.setUser(user);
		model.addAttribute("credentials", credentials);
		model.addAttribute("user", user);
		return "formNewRegistrazione";
	}

	@PostMapping("/registersalvata")
	public String salvataggioRegistrazione(@Valid @ModelAttribute Credentials credentials,
			BindingResult bindingCredentials,
			Model model) {
		this.credentialValidator.validate(credentials, bindingCredentials);
		if (bindingCredentials.hasErrors()) {
			return "formNewRegistrazione";
		}
		String encodedPassword = passwordEncoder.encode(credentials.getPassword());
		credentials.setPassword(encodedPassword);
		credentials.setRole(Credentials.DEFAULT_ROLE);
		credentialsService.saveCredentials(credentials);
		return "login";
	}

	@GetMapping("/success")
	public String loginSuccesso(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    	String username =  userDetails.getUsername();
    	Credentials credentials = credentialsService.getCredentials(username);
		if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
			return "indexAdmin";
		}
		if (credentials.getRole().equals(Credentials.DEFAULT_ROLE)) {
			return "index";
		}
		return "login";
	}
	
	@GetMapping("/")
		public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("squadre", squadraService.getSquadre());
		if(authentication instanceof AnonymousAuthenticationToken) {
			return "index" ;
		}
		else {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String username =  userDetails.getUsername();
	    	Credentials credentials = credentialsService.getCredentials(username);
	    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
				return "indexAdmin";
			}
		}
		return "index";
		
	}

}
