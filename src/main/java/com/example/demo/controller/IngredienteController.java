package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.controller.validator.IngredienteValidator;
import com.example.demo.model.Ingrediente;
import com.example.demo.model.Piatto;
import com.example.demo.service.IngredienteService;
import com.example.demo.service.PiattoService;

@Controller
public class IngredienteController {
	
	@Autowired
	IngredienteService ingredienteService;
	
	@Autowired
	IngredienteValidator ingredienteValidator;
	
	@Autowired
	PiattoService piattoService;
	
	
	@PostMapping("/admin/ingrediente")
	public String addIngrediente(@Valid @ModelAttribute("ingrediente") Ingrediente i, @RequestParam(name = "piattoScelto") Long id, BindingResult bindingResult, Model model){
		this.ingredienteValidator.validate(i, bindingResult);
		if(!bindingResult.hasErrors()) {
			Piatto p = this.piattoService.searchById(id);
			i.setPiatto(p);
			p.getIngredienti().add(i);
			this.piattoService.inserisci(p);
			model.addAttribute("ingrediente", i);
			return "ingrediente.html";
			
		}
		
		model.addAttribute("ingrediente", i);
		return "ingredienteForm.html";
	}
	
	@GetMapping("/elencoIngredienti")
	public String getAllIngredienti(Model model) {
		List<Ingrediente> elencoIngredienti = this.ingredienteService.findAllIngredienti();
		model.addAttribute("elencoIngredienti", elencoIngredienti);
		return "elencoIngredienti.html";
	}
	
	@GetMapping("/admin/ingredienteForm")
	public String getIngredienteForm(Model model) {
		model.addAttribute("ingrediente", new Ingrediente());
		model.addAttribute("piattiDisponibili", this.piattoService.findAllPiatti());
		return "ingredienteForm.html";
	}
	
	@GetMapping("/ingrediente/{id}")
	public String getIngrediente(@PathVariable("id") Long id, Model model) {
		Ingrediente ingrediente = this.ingredienteService.searchById(id);
		model.addAttribute("ingrediente", ingrediente);
		return "ingrediente.html";
	}
	
	@GetMapping("/admin/deleteIngrediente")
	public String deleteIngrediente(@RequestParam Long ingredienteId) {
		this.ingredienteService.rimuovi(ingredienteId);
		return "redirect:/elencoIngredienti";
	}
	
	@GetMapping("/admin/updateIngrediente")
    private String updateIngredienteForm(@RequestParam Long ingredienteId, Model model) {
        model.addAttribute("ingrediente", this.ingredienteService.searchById(ingredienteId));
        model.addAttribute("piattiDisponibili", this.piattoService.findAllPiatti());
        return "ingredienteUpdateForm.html";
    }

    @PostMapping("/admin/ingredienteUpdate/{id}")
    private String updateIngrediente(@Valid @ModelAttribute("ingrediente") Ingrediente ingrediente, 
    		@RequestParam(name = "piattoScelto") Long piattoId,
            BindingResult bindingResult,
            Model model) {

        this.ingredienteValidator.validate(ingrediente, bindingResult);
        if (!bindingResult.hasErrors()) {
            Piatto piattoNuovo = this.piattoService.searchById(piattoId);
            Piatto piattoVecchio = ingrediente.getPiatto();

            if(piattoVecchio!=null) {
                for(Ingrediente i : piattoVecchio.getIngredienti()) {
                    if(i.getId() == ingrediente.getId()) {
                        piattoVecchio.getIngredienti().remove(i);
                    }
                }
            }
            ingrediente.setPiatto(piattoNuovo);
            piattoNuovo.getIngredienti().add(ingrediente);
            this.piattoService.inserisci(piattoNuovo);
            model.addAttribute("ingrediente", ingrediente);
            return "ingrediente.html";
        }
        model.addAttribute("ingrediente", ingrediente);
        return "ingredienteUpdateForm.html";
    }
}
