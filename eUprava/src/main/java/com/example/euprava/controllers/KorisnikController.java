package com.example.euprava.controllers;

import com.example.euprava.Models.EUloga;
import com.example.euprava.Models.Korisnik;
import com.example.euprava.Models.Pacijent;
import com.example.euprava.Services.KorisnikService;
import com.example.euprava.Services.PacijentService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class KorisnikController {

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private PacijentService pacijentService;

    @GetMapping("/korisnici")
    public String prikazListeKorisnika(Model model, HttpServletRequest request) throws HttpClientErrorException.NotFound {
        List<Korisnik> lista = korisnikService.findAll();
        model.addAttribute("lista", lista);

        Cookie[] cookies = request.getCookies();
        if(korisnikService.checkCookies(cookies, EUloga.Administrator)){
            return "admin_pages/korisnici";
        }

        return "greska";
    }

    @GetMapping("/korisnici/{src}")
    public String prikazForme(Model model, @PathVariable("src") String src){
        if(src.equals("create")){
            model.addAttribute("navbar", "Dodavanje novog korisnika");
            model.addAttribute("redirect", "/eUprava/korisnici");
        }else if(src.equals("registracija")){
            model.addAttribute("navbar", "Registracija");
            model.addAttribute("redirect", "/eUprava/login");
        }
        model.addAttribute("korisnik", new Korisnik());
        model.addAttribute("url", "/korisnici/save");

        return "admin_pages/korisnik_edit";
    }


    @PostMapping("/korisnici/save")
    public String sacuvajKorisnika(Korisnik korisnik, RedirectAttributes redirectAttributes) throws HttpClientErrorException.NotFound {
        korisnikService.save(korisnik);
        System.out.println(korisnik);
        Korisnik noviKorisnik = korisnikService.findOneByEmail(korisnik.getEmail());

        System.out.println(noviKorisnik);
        if(korisnik.getUloga() == EUloga.Pacijent){
            Pacijent pacijent = new Pacijent(noviKorisnik);
            pacijentService.save(pacijent);
        }

        redirectAttributes.addFlashAttribute("poruka", "Korisnik je sacuvan");
        return "redirect:/";
    }

    @PostMapping("/korisnici/update")
    public String updateKorisnika(Korisnik korisnik) throws HttpClientErrorException.NotFound {
        Korisnik stariKorisnik = korisnikService.findOne(korisnik.getId());
        if(korisnik.getLozinka().isEmpty() || korisnik.getLozinka() == null){
            korisnik.setLozinka(stariKorisnik.getLozinka());
        }
        korisnik.setUloga(stariKorisnik.getUloga());
        korisnikService.update(korisnik);

        return "redirect:/profil";
    }

    @GetMapping("/korisnici/edit/{id}")
    public String prikazEditForme(@PathVariable("id")Long id, Model model, RedirectAttributes redirectAttributes){
        try{
            Korisnik korisnik = korisnikService.findOne(id);
            model.addAttribute("korisnik", korisnik);
            model.addAttribute("naslov", "Izmena podataka korisnika");
            model.addAttribute("url", "/korisnici/update");
            return "admin_pages/korisnik_edit";
        }catch (HttpClientErrorException.NotFound ex){
            return "redirect:/profil";
        }
    }

    @GetMapping("/korisnici/delete/{id}")
    public String deleteKorisnik(@PathVariable("id")Long id, RedirectAttributes redirectAttributes){
        korisnikService.delete(id);
        pacijentService.delete(id);
        redirectAttributes.addFlashAttribute("poruka", "Korisnik obrisan");
        return "redirect:/korisnici";
    }

    @GetMapping("/profil")
    public String profile(Model model, HttpServletRequest request) throws HttpClientErrorException.NotFound{
        Cookie[] cookies = request.getCookies();
        Korisnik korisnik = korisnikService.checkCookieUser(cookies);
        model.addAttribute("korisnik", korisnik);
        if(korisnik.getUloga().equals(EUloga.Pacijent)){
            model.addAttribute("uloga", "pacijent");
        }else if(korisnik.getUloga().equals(EUloga.Administrator)){
            model.addAttribute("uloga", "admin");
        }else if(korisnik.getUloga().equals(EUloga.Medicinsko_osoblje)){
            model.addAttribute("uloga", "osoblje");
        }
        return "profil";
    }
}
