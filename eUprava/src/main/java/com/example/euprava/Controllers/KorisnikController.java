package com.example.euprava.Controllers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


import com.example.euprava.Models.Korisnik;
import com.example.euprava.Services.KorisnikService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping(value = "korisnici")
public class KorisnikController implements ServletContextAware{
    @Autowired
    private KorisnikService korisnikService;
    @GetMapping
    @ResponseBody
    public  ModelAndView getKorisnici(HttpSession session, HttpServletResponse response){
        List<Korisnik> korisnici = korisnikService.findAll();
        ModelAndView rezultat = new ModelAndView("korisnici");
        rezultat.addObject("korisnici", korisnici);
        return rezultat;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {

    }
}
