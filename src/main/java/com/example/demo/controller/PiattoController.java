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

import com.example.demo.controller.validator.PiattoValidator;
import com.example.demo.model.Buffet;
import com.example.demo.model.Piatto;
import com.example.demo.service.BuffetService;
import com.example.demo.service.PiattoService;

@Controller
public class PiattoController {
	
	@Autowired
	PiattoService piattoService;
	
	@Autowired 
	PiattoValidator piattoValidator;
	
	@Autowired
	BuffetService buffetService;
	
	
	
	@PostMapping("/admin/piatto")
	public String addPiatto(@Valid @ModelAttribute("piatto") Piatto p, @RequestParam(name = "buffetScelto") Long id, BindingResult bindingResult, Model model) {
		this.piattoValidator.validate(p, bindingResult);
		if(!bindingResult.hasErrors()) {
			Buffet b = this.buffetService.searchById(id);
			p.setBuffet(b);
			b.getPiatti().add(p);
			this.buffetService.inserisci(b);
			model.addAttribute("piatto", p);
			model.addAttribute("elencoIngredienti", p.getIngredienti());
			return "piatto.html";
		}
		model.addAttribute("piatto", p);
		return "piattoForm.html";
	}
	
	@GetMapping("/elencoPiatti")
	public String getAllPiatti(Model model) {
		List<Piatto> elencoPiatti = this.piattoService.findAllPiatti();
		model.addAttribute("elencoPiatti", elencoPiatti);
		return "elencoPiatti.html";
	}
	
	@GetMapping("/admin/piattoForm")
	public String getPiattoForm(Model model) {
		model.addAttribute("piatto", new Piatto());
		model.addAttribute("buffetDisponibili", this.buffetService.findAllBuffet());
		return "piattoForm.html";
	}
	
	@GetMapping("/piatto/{id}")
	public String getPiatto(@PathVariable("id") Long id, Model model) {
		Piatto piatto = this.piattoService.searchById(id);
		model.addAttribute("piatto", piatto);
		model.addAttribute("elencoIngredienti", piatto.getIngredienti());
		return "piatto.html";
	}
	
	@GetMapping("/admin/deletePiatto")
	public String deletePiatto(@RequestParam Long piattoId) {
		this.piattoService.rimuovi(piattoId);
		return "redirect:/elencoPiatti";
	}
	
	@GetMapping("/admin/updatePiatto")
    private String updatePiattoForm(@RequestParam Long piattoId, Model model) {
        model.addAttribute("piatto", this.piattoService.searchById(piattoId));
        model.addAttribute("buffetDisponibili", this.buffetService.findAllBuffet());
        return "piattoUpdateForm.html";
    }

    @PostMapping("/admin/piattoUpdate/{id}")
    private String updatePiatto(@Valid @ModelAttribute("piatto") Piatto piatto, 
    		@RequestParam(name = "buffetScelto") Long buffetId,
            BindingResult bindingResult,
            Model model) {

        this.piattoValidator.validate(piatto, bindingResult);
        if (!bindingResult.hasErrors()) {
            Buffet buffetNuovo = this.buffetService.searchById(buffetId);
            Buffet buffetVecchio = piatto.getBuffet();

            if(buffetVecchio!=null) {
                for(Piatto p : buffetVecchio.getPiatti()) {
                    if(p.getId() == piatto.getId()) {
                        buffetVecchio.getPiatti().remove(p);
                    }
                }
            }
            piatto.setBuffet(buffetNuovo);
            buffetNuovo.getPiatti().add(piatto);
            this.buffetService.inserisci(buffetNuovo);
            model.addAttribute("piatto", piatto);
            model.addAttribute("elencoIngredienti", piatto.getIngredienti());
            return "piatto.html";
        }
        model.addAttribute("piatto", piatto);
        return "piattoUpdateForm.html";
    }
	
}
