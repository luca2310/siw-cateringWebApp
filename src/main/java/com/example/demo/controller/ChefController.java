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

import com.example.demo.controller.validator.ChefValidator;
import com.example.demo.model.Chef;
import com.example.demo.service.ChefService;

@Controller
public class ChefController {
	
	@Autowired
	ChefService chefService;
	
	@Autowired
	ChefValidator chefValidator;
	
	
	
	@PostMapping("/admin/chef")
	public String addChef(@Valid @ModelAttribute("chef") Chef c, BindingResult bindingResult, Model model) {
		this.chefValidator.validate(c, bindingResult);
		if(!bindingResult.hasErrors()) {
			this.chefService.inserisci(c);
			model.addAttribute("chef", this.chefService.searchById(c.getId()));
			model.addAttribute("elencoBuffet", c.getBuffet());
			return "chef.html";
		}
		else {
			return "chefForm.html";
		}
	}
	
	@GetMapping("/elencoChef")
	public String getAllChef(Model model) {
		List<Chef> elencoChef = this.chefService.findAllChef();
		model.addAttribute("elencoChef", elencoChef);
		return "elencoChef.html";
	}
	
	@GetMapping("/admin/chefForm")
	public String getChefForm(Model model) {
		model.addAttribute("chef", new Chef());
		return "chefForm.html";
	}
	
	@GetMapping("/chef/{id}")
	public String getChef(@PathVariable("id") Long id, Model model) {
		Chef chef = this.chefService.searchById(id);
		model.addAttribute("chef", chef);
		model.addAttribute("elencoBuffet", chef.getBuffet());
		return "chef.html";
	}
	
	@GetMapping("/admin/deleteChef")
	public String deleteChef(@RequestParam Long chefId) {
		this.chefService.rimuovi(chefId);
		return "redirect:/elencoChef";
	}
	
	
	@GetMapping("/admin/updateChef")
    private String updateBuffetForm(@RequestParam Long chefId, Model model) {
        model.addAttribute("chef", this.chefService.searchById(chefId));
        return "chefUpdateForm.html";
    }

    @PostMapping("/admin/chefUpdate/{id}")
    private String updateChef(@Valid @ModelAttribute("chef") Chef chef, BindingResult bindingResult, Model model) {

        this.chefValidator.validate(chef, bindingResult);
        if (!bindingResult.hasErrors()) {
//            Chef chefNuovo = this.chefService.searchById(chefId);
//            Chef chefVecchio = buffet.getChef();
//
//            if(chefVecchio!=null) {
//                for(Buffet b : chefVecchio.getBuffet()) {
//                    if(b.getId() == buffet.getId()) {
//                        chefVecchio.getBuffet().remove(b);
//                    }
//                }
//            }
//            buffet.setChef(chefNuovo);
//            chefNuovo.getBuffet().add(buffet);
//            this.chefService.inserisci(chefNuovo);
            model.addAttribute("chef", chef);
            model.addAttribute("elencoBuffet", chef.getBuffet());
            return "chef.html";
        }
        model.addAttribute("chef", chef);
        return "chefUpdateForm.html";
    }
}
