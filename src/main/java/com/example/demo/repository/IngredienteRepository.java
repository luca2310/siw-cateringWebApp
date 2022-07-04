package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Ingrediente;

public interface IngredienteRepository extends CrudRepository<Ingrediente, Long>{

	public Ingrediente findByNome(String s);

}
