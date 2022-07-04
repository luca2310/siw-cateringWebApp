package com.example.demo.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.Chef;
import com.example.demo.service.ChefService;

@Component
public class ChefValidator implements Validator {

	@Autowired
	ChefService chefService;
	
	@Override
	public void validate(Object o, Errors errors) {
		if(this.chefService.alreadyExists((Chef) o)) {
			errors.reject("chef.duplicato");
		}
	}
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Chef.class.equals(aClass);
	}


}
