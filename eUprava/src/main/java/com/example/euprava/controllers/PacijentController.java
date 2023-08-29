package com.example.euprava.controllers;

import com.example.euprava.Models.Vakcina;
import com.example.euprava.Services.VakcinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PacijentController {

    @Autowired
    private VakcinaService vakcinaService;

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
}
