package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Ingrediente;
import com.example.demo.model.Piatto;
import com.example.demo.repository.PiattoRepository;

@Service
public class PiattoService {

	@Autowired 
	PiattoRepository piattoRepository;
	
	@Autowired
	IngredienteService is;
	
	
	public boolean alreadyExists(Piatto p) {			
		return this.findAllPiatti().contains(p);
	}
	
	@Transactional
	public Piatto inserisci(Piatto piatto) {
		return this.piattoRepository.save(piatto);
	}
	
	public void inserisciPiattoIngrediente(Ingrediente i) {
		this.is.inserisci(i);
		
	}
	
	@Transactional
	public void rimuovi(Long id) {
		this.piattoRepository.deleteById(id);
	}
	
	@Transactional
	public void clear() {
		this.piattoRepository.deleteAll();
	}
	
	public Piatto searchById(Long id) {
		return this.piattoRepository.findById(id).get();
	}
	
	public Piatto searchByNome(String nome) {
		return this.piattoRepository.findByNome(nome);
	}
	
	public List<Piatto> findAllPiatti(){
		List<Piatto> elencoPiatti = new ArrayList<Piatto>();
		for(Piatto p : this.piattoRepository.findAll()) {
			elencoPiatti.add(p);
		}
		return elencoPiatti;
	}
}
