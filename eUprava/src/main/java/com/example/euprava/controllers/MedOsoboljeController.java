package com.example.euprava.controllers;

import com.example.euprava.Models.Vakcina;
import com.example.euprava.Services.KorisnikService;
import com.example.euprava.Services.ProizvodjacVakcinaService;
import com.example.euprava.Services.VakcinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String prikazVakcina(Model model, @RequestParam(name = "pretraga", required = false) String pretraga){

        List<Vakcina> vakcine = new ArrayList<>();

        if(pretraga != null && !pretraga.isEmpty()){
            vakcine = vakcinaService.search(pretraga);
        }

        model.addAttribute("listaVakcina", vakcine);
        return "medicinsko_osoblje_pages/vakcine";
    }
}
