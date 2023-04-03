package com.example.euprava.controllers;

import com.example.euprava.Models.Vest;
import com.example.euprava.Services.VestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/Vesti")
public class VestController{
    @Autowired
    private VestService vestService;

    @GetMapping
    public ModelAndView index() {
        List<Vest> vesti = vestService.findAll();

        ModelAndView rezultat = new ModelAndView("pocetna");
        rezultat.addObject("vesti", vesti);

        return rezultat;
    }

}
