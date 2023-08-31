package com.example.euprava.controllers;

import com.example.euprava.Models.EUloga;
import com.example.euprava.Models.Korisnik;
import com.example.euprava.Models.ProizvodjacVakcina;
import com.example.euprava.Services.KorisnikService;
import com.example.euprava.Services.ProizvodjacVakcinaService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProizvodjacVakcinaController {

    @Autowired
    private ProizvodjacVakcinaService proizvodjacVakcinaService;
    @Autowired
    private KorisnikService korisnikService;


    @GetMapping("/proizvodjacVakcina")
    public String prikazProizvodjacaVakcina(Model model, HttpServletRequest request){
        List<ProizvodjacVakcina> lista = proizvodjacVakcinaService.findAll();
        model.addAttribute("listaProizvodjaca", lista);

        Cookie[] cookies = request.getCookies();
        if(korisnikService.checkCookies(cookies, EUloga.Administrator)){
            return "admin_pages/proizvodjaci_vakcina";
        }

        return "greska";
    }

    @GetMapping("/proizvodjacVakcina/create")
    public String prikazForme(Model model){
        model.addAttribute("proizvodjac", new ProizvodjacVakcina());
        model.addAttribute("naslov", "Kreiranje proizvodjaca");
        return "admin_pages/proizvodjac_vakcina_edit";
    }

    @PostMapping("/proizvodjacVakcina/save")
    public String createProizvodjaca(ProizvodjacVakcina proizvodjacVakcina, RedirectAttributes redirectAttributes){
        proizvodjacVakcinaService.save(proizvodjacVakcina);
        redirectAttributes.addFlashAttribute("poruka", "Proizvodjac vakcina je sacuvan");
        return "redirect:/proizvodjacVakcina";
    }

    @GetMapping("/proizvodjacVakcina/edit/{id}")
    public String editProizvodjaca(@PathVariable("id")Long id, Model model){
        ProizvodjacVakcina proizvodjacVakcina = proizvodjacVakcinaService.findOne(id);
        model.addAttribute("proizvodjac", proizvodjacVakcina);
        model.addAttribute("naslov", "Izmena podataka proizvodjaca");
        return "admin_pages/proizvodjac_vakcina_edit";
    }

    @GetMapping("/proizvodjacVakcina/delete/{id}")
    public String deleteProizvodjac(@PathVariable("id")Long id, RedirectAttributes redirectAttributes){
        proizvodjacVakcinaService.delete(id);
        redirectAttributes.addFlashAttribute("poruka", "Proizvodjac vakcina je obrisan");
        return "redirect:/proizvodjacVakcina";
    }
}
