package com.example.euprava.controllers;

import com.example.euprava.Models.Vest;
import com.example.euprava.Services.VestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class VestController {

    @Autowired
    private VestService vestService;

    @GetMapping("/vesti")
    public String prikazListeVesti(Model model){
        List<Vest> lista = vestService.findAll();
        model.addAttribute("vest", new Vest());
        model.addAttribute("listaVesti", lista);
        return "admin_pages/vesti";
    }

    @GetMapping("/vesti/create")
    public String prikazNoveForme(Model model){
        model.addAttribute("vest", new Vest());
        return "admin_pages/vesti_edit";
    }

    @PostMapping("/vesti/save")
    public String sacuvajVest(Vest vest, RedirectAttributes redirectAttributes){
        vestService.save(vest);
        redirectAttributes.addFlashAttribute("poruka", "Vest je sacuvana");
        return "redirect:/vesti";
    }

    @GetMapping("/vesti/edit/{id}")
    public String editVest(@PathVariable("id") Long id, Model model){
        Vest vest = vestService.findOne(id);
        model.addAttribute("vest", vest);
        return "admin_pages/vesti_edit";
    }

    @GetMapping("/vesti/delete/{id}")
    public String deleteVest(@PathVariable("id")Long id, RedirectAttributes redirectAttributes){
        vestService.delete(id);
        redirectAttributes.addFlashAttribute("poruka", "Vest je obrisana");
        return "redirect:/vesti";
    }
}
