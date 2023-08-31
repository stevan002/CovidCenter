package com.example.euprava.controllers;

import com.example.euprava.Models.*;
import com.example.euprava.Services.KorisnikService;
import com.example.euprava.Services.PacijentService;
import com.example.euprava.Services.PrijavaService;
import com.example.euprava.Services.VakcinaService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PrijavaController {
    @Autowired
    private PacijentService pacijentService;
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private VakcinaService vakcinaService;
    @Autowired
    private PrijavaService prijavaService;

    @GetMapping("/prijave")
    public String prikazPrijava(Model model, HttpServletRequest request, @RequestParam(name = "pretraga", required = false) String pretraga, RedirectAttributes redirectAttributes)throws HttpClientErrorException.NotFound{
        List<Prijava> prijave;

        if (pretraga != null && !pretraga.isEmpty()) {
            prijave = prijavaService.search(pretraga);
        }else{
            prijave = prijavaService.findAll();
        }
        model.addAttribute("listaPrijava", prijave);

        if(redirectAttributes.getFlashAttributes().containsKey("poruka")){
            String poruka = (String) redirectAttributes.getFlashAttributes().get("poruka");
            model.addAttribute("poruka", poruka);
        }

        Cookie[] cookies = request.getCookies();
        if(korisnikService.checkCookies(cookies, EUloga.Medicinsko_osoblje)){
            return "medicinsko_osoblje_pages/prijave";
        }
        return "greska";
    }

    @PostMapping("/prijava/new")
    public String savePrijava(@RequestParam("vakcinaId") Long vakcinaId, HttpServletRequest request, RedirectAttributes redirectAttributes) throws HttpClientErrorException.NotFound{
        Vakcina vakcina = vakcinaService.findOne(vakcinaId);
        Cookie[] cookies = request.getCookies();
        Korisnik korisnik = korisnikService.checkCookieUser(cookies);

        if(korisnik.getUloga().equals(EUloga.Pacijent)){
            Pacijent pacijent = pacijentService.findOne(korisnik.getId());
            Prijava prijava = new Prijava();
            System.out.println(pacijent);
            prijava.setVremeDobijanjaDoze(LocalDateTime.now());
            prijava.setPacijent(pacijent);
            prijava.setVakcina(vakcina);
            System.out.println(pacijent);
            prijavaService.save(prijava);
            redirectAttributes.addFlashAttribute("poruka", "Napravljena prijava");
        }else {
            redirectAttributes.addFlashAttribute("poruka", "neuspesna prijava");
        }
        return "redirect:/pacijent/vakcine";
    }

    @PostMapping("/prijave/create")
    public String createPrijava(@RequestParam("prijavaId")Long prijavaId, HttpServletRequest request, RedirectAttributes redirectAttributes) throws HttpClientErrorException.NotFound{
        Prijava prijava = prijavaService.findOne(prijavaId);
        Cookie[] cookies = request.getCookies();
        Korisnik korisnik = korisnikService.checkCookieUser(cookies);

        Pacijent pacijent = prijava.getPacijent();

        if(pacijent.getDoze() < 4){
            if((pacijent.getDoze() == 1 && prijavaService.isPast(1, pacijent.getVremePrimanjaDoze()))
        || (pacijent.getDoze() == 2 && prijavaService.isPast(1, pacijent.getVremePrimanjaDoze()))
        || (pacijent.getDoze() == 3 && prijavaService.isPast(1, pacijent.getVremePrimanjaDoze()))
            || pacijent.getDoze() == 0) {
                if(korisnik.getUloga().equals(EUloga.Medicinsko_osoblje)){
                    Vakcina vakcina = prijava.getVakcina();
                    vakcina.setKolicina(vakcina.getKolicina() - 1);
                    pacijent.setVremePrimanjaDoze(LocalDateTime.now());
                    pacijent.setDoze(pacijent.getDoze() + 1);

                    prijavaService.deleteByPacijent(pacijent.getKorisnikId());
                    vakcinaService.save(vakcina);
                    pacijentService.update(pacijent);

                    redirectAttributes.addFlashAttribute("poruka", "vakcinisan pacijent");
                }
            }else{
                redirectAttributes.addFlashAttribute("poruka", "Mora da prodje vise vremena za sledecu dozu");
            }
        }else{
            redirectAttributes.addFlashAttribute("poruka", "Pacijent je primio maksimalan broj doza(4) doze");
        }
        return "redirect:/prijave";
    }

    @GetMapping("/prijave/delete/{id}")
    public String deleteVakcina(@PathVariable("id")Long id, RedirectAttributes redirectAttributes){
        prijavaService.delete(id);
        redirectAttributes.addFlashAttribute("poruka", "Prijava je izbrisana");

        return "redirect:/prijave";
    }


}
