package com.example.euprava.controllers;

import com.example.euprava.Models.EUloga;
import com.example.euprava.Models.Vest;
import com.example.euprava.Models.VestObolelima;
import com.example.euprava.Services.KorisnikService;
import com.example.euprava.Services.VestObolelimaService;
import com.example.euprava.Services.VestService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PocetnaController {

    @Autowired
    private VestService vestService;
    @Autowired
    private VestObolelimaService vestObolelimaService;
    @Autowired
    private KorisnikService korisnikService;

    @GetMapping("/")
    public String pocetna(Model model, HttpServletRequest request){
        List<Vest> list = vestService.findAll();
        model.addAttribute("listaVesti", list);

        VestObolelima vestObolelimaToday = vestObolelimaService.findLastInserted();
        model.addAttribute("vestDanas", vestObolelimaToday);

        Cookie[] cookies = request.getCookies();
        if(korisnikService.checkCookies(cookies, EUloga.Administrator)){
            model.addAttribute("uloga", "admin");
        }
        else if(korisnikService.checkCookies(cookies, EUloga.Medicinsko_osoblje)){
            model.addAttribute("uloga", "osoblje");
        }
        else if(korisnikService.checkCookies(cookies, EUloga.Pacijent)){
            model.addAttribute("uloga", "pacijent");
        } else{
            model.addAttribute("uloga", "none");
        }

        return "pocetna";
    }
}
