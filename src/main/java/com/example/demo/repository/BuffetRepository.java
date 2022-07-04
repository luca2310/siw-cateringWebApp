package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Buffet;

public interface BuffetRepository extends CrudRepository<Buffet, Long> {
	
	public Buffet findByNome(String s);
	
}
