package com.example.euprava.controllers;

import com.example.euprava.Models.EUloga;
import com.example.euprava.Models.Korisnik;
import com.example.euprava.Models.Pacijent;
import com.example.euprava.Services.KorisnikService;
import com.example.euprava.Services.PacijentService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class KorisnikController {

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private PacijentService pacijentService;

    @GetMapping("/korisnici")
    public String prikazListeKorisnika(Model model){
        List<Korisnik> lista = korisnikService.findAll();
        model.addAttribute("lista", lista);

        return "admin_pages/korisnici";
    }

    @GetMapping("/korisnici/create")
    public String prikazForme(Model model){
        model.addAttribute("naslov", "Dodavanje korisnika");
        model.addAttribute("korisnik", new Korisnik());

        return "admin_pages/korisnik_edit";
    }

    @PostMapping("/korisnici/save")
    public String sacuvajKorisnika(Korisnik korisnik, RedirectAttributes redirectAttributes){
        korisnikService.save(korisnik);
//        String email = korisnik.getEmail();
//        Korisnik korisnik1 = korisnikService.findOneByEmail(email);

//        System.out.println(korisnik1);
//        if(korisnik.getUloga() == EUloga.Pacijent){
//            Pacijent pacijent = new Pacijent(korisnik1);
//            pacijentService.save(pacijent);
//        }

        redirectAttributes.addFlashAttribute("poruka", "Korisnik je sacuvan");
        return "redirect:/korisnici";
    }

    @GetMapping("/korisnici/edit/{id}")
    public String prikazEditForme(@PathVariable("id")Long id, Model model, RedirectAttributes redirectAttributes){
        Korisnik korisnik = korisnikService.findOne(id);
        model.addAttribute("korisnik", korisnik);
        model.addAttribute("naslov", "Izmena podataka korisnika");
        return "admin_pages/korisnik_edit";
    }

    @GetMapping("/korisnici/delete/{id}")
    public String deleteKorisnik(@PathVariable("id")Long id, RedirectAttributes redirectAttributes){
        korisnikService.delete(id);
        pacijentService.delete(id);
        redirectAttributes.addFlashAttribute("poruka", "Korisnik obrisan");
        return "redirect:/korisnici";
    }
}
