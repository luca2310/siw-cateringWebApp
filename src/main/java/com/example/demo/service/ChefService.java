package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Chef;
import com.example.demo.repository.ChefRepository;

@Service
public class ChefService {

	@Autowired
	ChefRepository chefRepository;
	
	public boolean alreadyExists(Chef c) {
		return this.findAllChef().contains(c);
	}
	
	@Transactional
	public Chef inserisci(Chef chef) {
		return this.chefRepository.save(chef);
	}
	
	@Transactional
	public void rimuovi(Long id) {
		this.chefRepository.deleteById(id);
	}
	
	@Transactional
	public void clear() {
		this.chefRepository.deleteAll();
	}
	
	public Chef searchById(Long id) {
		return this.chefRepository.findById(id).get();
	}
	
	public List<Chef> findAllChef(){
		List<Chef> elencoChef = new ArrayList<Chef>();
		for(Chef c : this.chefRepository.findAll()) {
			elencoChef.add(c);
		}
		return elencoChef;
	}
}
