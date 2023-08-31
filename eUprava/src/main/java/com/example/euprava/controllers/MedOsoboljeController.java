package com.example.euprava.controllers;

import com.example.euprava.Models.EUloga;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MedOsoboljeController {

    @Autowired
    private VakcinaService vakcinaService;

    @Autowired
    private ProizvodjacVakcinaService proizvodjacVakcinaService;

    @Autowired
    private KorisnikService korisnikService;

    @GetMapping("/medicinskoOsoblje/vakcine")
    public String prikazVakcina(Model model, @RequestParam(name = "pretraga", required = false) String pretraga,
                                @RequestParam(name = "order", required = false) String order,
                                @RequestParam(name = "orderBy", required = false) String orderBy,
                                @RequestParam(name = "min", required = false) Integer min,
                                @RequestParam(name = "max", required = false) Integer max,
                                RedirectAttributes redirectAttributes, HttpServletRequest request) throws HttpClientErrorException.NotFound {


        if(order == null){
            order = "id";
        }
        if(orderBy == null){
            orderBy = "asc";
        }

        List<Vakcina> vakcine;

        if(pretraga != null && !pretraga.isEmpty()){
            vakcine = vakcinaService.search(pretraga);
        }else if(min != null && max != null){
            vakcine = vakcinaService.searchKolicina(min, max);
        }else{
            vakcine = vakcinaService.sortiranje(order, orderBy);
        }

        model.addAttribute("listaVakcina", vakcine);
        model.addAttribute("newOrderBy", orderBy.equals("asc") ? "desc" : "asc");

        Cookie[] cookies = request.getCookies();
        if(korisnikService.checkCookies(cookies, EUloga.Medicinsko_osoblje)){
            return "medicinsko_osoblje_pages/vakcine";
        }
        return "greska";
    }
}
