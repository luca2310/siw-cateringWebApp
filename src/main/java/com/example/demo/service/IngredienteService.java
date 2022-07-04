package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Ingrediente;
import com.example.demo.repository.IngredienteRepository;

@Service
public class IngredienteService {
	
	@Autowired
	IngredienteRepository ingredienteRepository;
	

	public boolean alreadyExists(Ingrediente i) {
		return this.findAllIngredienti().contains(i);
	}
	
	@Transactional
	public Ingrediente inserisci(Ingrediente ingrediente) {
		return this.ingredienteRepository.save(ingrediente);
	}
	
	@Transactional
	public void rimuovi(Long id) {
		this.ingredienteRepository.deleteById(id);
	}
	
	@Transactional
	public void clear() {
		this.ingredienteRepository.deleteAll();
	}
	
	public Ingrediente searchById(Long id) {
		return this.ingredienteRepository.findById(id).get();
	}
	
	public Ingrediente searchByNome(String nome) {
		return this.ingredienteRepository.findByNome(nome);
	}
	
	public List<Ingrediente> findAllIngredienti() {
		List<Ingrediente> elencoIngredienti = new ArrayList<Ingrediente>();
		for(Ingrediente i : this.ingredienteRepository.findAll()) {
			elencoIngredienti.add(i);
		}
		return elencoIngredienti;
	}
}
