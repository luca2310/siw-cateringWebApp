package com.example.demo.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.Piatto;
import com.example.demo.service.PiattoService;

@Component
public class PiattoValidator implements Validator {

	@Autowired
	PiattoService piattoService;
	
	@Override
	public void validate(Object o, Errors errors) {
		if(this.piattoService.alreadyExists((Piatto) o)) {
			errors.reject("piatto.duplicato");
		}
	}
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Piatto.class.equals(aClass);
	}


}
