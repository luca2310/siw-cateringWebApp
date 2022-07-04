package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Buffet;
import com.example.demo.repository.BuffetRepository;

@Service
public class BuffetService {

	@Autowired
	BuffetRepository buffetRepository;
	

	public boolean alreadyExists(Buffet b) {
		return this.findAllBuffet().contains(b);
	}
	
	@Transactional
	public Buffet inserisci(Buffet buffet) {
		return this.buffetRepository.save(buffet);
	}
	
	@Transactional
	public void rimuovi(Long id) {
		this.buffetRepository.deleteById(id);
	}
	
	@Transactional
	public void clear() {
		this.buffetRepository.deleteAll();
	}
	
	public Buffet searchById(Long id) {
		return this.buffetRepository.findById(id).get();
	}
	
	public List<Buffet> findAllBuffet(){
		List<Buffet> elencoBuffet = new ArrayList<Buffet>();
		for(Buffet b : this.buffetRepository.findAll()) {
			elencoBuffet.add(b);
		}
		return elencoBuffet;
	}
}
