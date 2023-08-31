package com.example.euprava.controllers;

import com.example.euprava.Models.EUloga;
import com.example.euprava.Models.Pacijent;
import com.example.euprava.Models.Vakcina;
import com.example.euprava.Services.KorisnikService;
import com.example.euprava.Services.PacijentService;
import com.example.euprava.Services.VakcinaService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PacijentController {

    @Autowired
    private VakcinaService vakcinaService;
    @Autowired
    private PacijentService pacijentService;
    @Autowired
    private KorisnikService korisnikService;

    @GetMapping("/pacijent/vakcine")
    public String prikazVakcina(Model model){
        List<Vakcina> lista = vakcinaService.findAll();
        List<Vakcina> praznaLista = new ArrayList<>();
        if(lista != null){
            for(Vakcina vakcina: lista){
                if(vakcina.getKolicina() > 0){
                    praznaLista.add(vakcina);
                }
            }
        }

        model.addAttribute("listaVakcina", praznaLista);

        return "pacijent_pages/vakcine";
    }

    @GetMapping("/pacijenti")
    public String prikazListKartona(Model model, HttpServletRequest request) throws HttpClientErrorException.NotFound{
        List<Pacijent> lista = pacijentService.listAll();
        model.addAttribute("listaPacijenta", lista);

        Cookie[] cookies = request.getCookies();
        if(korisnikService.checkCookies(cookies, EUloga.Administrator)){
            return "admin_pages/pacijenti";
        }

        return "greska";
    }

    @GetMapping("/pacijenti/update")
    public String updatePacijent(Pacijent pacijent, RedirectAttributes redirectAttributes){
        pacijentService.update(pacijent);
        redirectAttributes.addFlashAttribute("message", "Pacijent je izmenjen");
        return "redirect:/pacijenti";
    }

    @GetMapping("/pacijenti/edit/{id}")
    public String prikazForme(@PathVariable("id") Long id, Model model){
        Pacijent pacijent = pacijentService.findOne(id);
        model.addAttribute("pacijent", pacijent);

        return "admin_pages/pacijenti_edit";
    }

    @GetMapping("/pacijenti/delete/{id}")
    public String deletePacijent(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        korisnikService.delete(id);
        pacijentService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Karton pacijenta i pacijent su obrisani");
        return "redirect:/pacijenti";
    }
}
