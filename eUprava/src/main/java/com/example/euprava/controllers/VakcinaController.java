package com.example.euprava.controllers;

import com.example.euprava.Models.EUloga;
import com.example.euprava.Models.ProizvodjacVakcina;
import com.example.euprava.Models.Vakcina;
import com.example.euprava.Services.KorisnikService;
import com.example.euprava.Services.ProizvodjacVakcinaService;
import com.example.euprava.Services.VakcinaService;
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
public class VakcinaController {

    @Autowired
    private VakcinaService vakcinaService;

    @Autowired
    private ProizvodjacVakcinaService proizvodjacVakcinaService;

    @Autowired
    private KorisnikService korisnikService;

    @GetMapping("/vakcina")
    public String prikazVakcina(Model model, HttpServletRequest request){
        List<Vakcina> lista = vakcinaService.findAll();
        model.addAttribute("listaVakcina", lista);

        Cookie[] cookies = request.getCookies();
        if(korisnikService.checkCookies(cookies, EUloga.Administrator)){
            return "admin_pages/vakcine";
        }

        return "greska";
    }

    @GetMapping("/vakcina/create")
    public String prikazForme(Model model){
        List<ProizvodjacVakcina> listaProizvodjaca = proizvodjacVakcinaService.findAll();

        model.addAttribute("vakcina", new Vakcina());
        model.addAttribute("proizvodjaciVakcina", listaProizvodjaca);
        model.addAttribute("naslov", "Kreiranje vakcine");
        return "admin_pages/vakcina_edit";
    }

    @PostMapping("vakcina/save")
    public String saveVakcina(Vakcina vakcina, RedirectAttributes redirectAttributes){
        vakcina.setProizvodjac(proizvodjacVakcinaService.findOne(vakcina.getProizvodjacId()));
        vakcinaService.save(vakcina);
        redirectAttributes.addFlashAttribute("poruka", "Vakcina je sacuvana");
        return "redirect:/vakcina";
    }

    @GetMapping("vakcina/edit/{id}")
    public String prikazEditForme(@PathVariable("id")Long id, Model model){
        List<ProizvodjacVakcina> proizvodjaciVakcina = proizvodjacVakcinaService.findAll();
        Vakcina vakcina = vakcinaService.findOne(id);
        model.addAttribute("proizvodjaciVakcina", proizvodjaciVakcina);
        model.addAttribute("proizvodjacVakcina", vakcina.getProizvodjac());
        model.addAttribute("vakcina", vakcina);
        model.addAttribute("naslov", "Izmena vakcine");
        return "admin_pages/vakcina_edit";
    }

    @GetMapping("/vakcina/delete/{id}")
    public String deleteVakcina(@PathVariable("id")Long id, RedirectAttributes redirectAttributes){
        vakcinaService.delete(id);
        redirectAttributes.addFlashAttribute("poruka", "Vakcina je izbrisana");

        return "redirect:/vakcina";
    }
}
