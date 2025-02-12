package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.repository.CredentialsRepository;
import jakarta.transaction.Transactional;

@Service
public class CredentialsService {
	@Autowired
	CredentialsRepository credentialsRepository;

	public Credentials getCredentials(Long id) {
		return this.credentialsRepository.findById(id).get();
	}
	
	public Credentials getCredentials(String username) {
		return this.credentialsRepository.findByUsername(username).get();
	}

	@Transactional
	public Credentials saveCredentials(Credentials credentials) {
		return this.credentialsRepository.save(credentials);
	}
	public boolean utenteEsistente(String username) {
		return credentialsRepository.existsByUsername(username);
	}
}
