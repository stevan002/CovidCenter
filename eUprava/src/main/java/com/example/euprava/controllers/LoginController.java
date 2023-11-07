package com.example.euprava.controllers;

import com.example.euprava.Models.Korisnik;
import com.example.euprava.Services.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    private KorisnikService korisnikService;

    private Map<String, String> loggedInUsers = new HashMap<>();

    @GetMapping("/login")
    public String prikazForme(){
        return "login";
    }

    @PostMapping("/login/save")
    public String login(@RequestParam("email") String email, @RequestParam("lozinka") String lozinka,
                        HttpServletResponse response, Model model,
                        @CookieValue(value = "sessionID", defaultValue = "") String sessionID)
            throws HttpClientErrorException.NotFound{
        Korisnik korisnik = korisnikService.findOneByEmailAndPassword(email, lozinka);
        System.out.println(korisnik);
        if(korisnik != null){
            if(isUserLoggedIn(korisnik.getEmail())){
                model.addAttribute("greska", "Korisnik je ulogovan");
                setSessionCookie(response, korisnik.getEmail());
                return "redirect:/";
            }

            if(sessionID.isEmpty()){
                sessionID = generateSessionID();
                setSessionCookie(response, korisnik.getEmail());
            }

            setLoggedInUsers(sessionID, korisnik.getEmail());
        }else{
            model.addAttribute("greska", "Pogresan email ili lozinka");

        }

        model.addAttribute("greska", "Pogresan email ili lozinka");
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response, @CookieValue(value = "sessionID") String sessionID){
        loggedInUsers.remove(sessionID);

        Cookie sessionCookie = new Cookie("sessionID", null);
        sessionCookie.setMaxAge(0);
        sessionCookie.setPath("/");
        response.addCookie(sessionCookie);

        return "redirect:/login";
    }

    private boolean isUserLoggedIn(String email){
        return loggedInUsers.containsValue(email);
    }

    private void setSessionCookie(HttpServletResponse response, String sessionID){
        Cookie sessionCookie = new Cookie("sessionID", sessionID);
        sessionCookie.setMaxAge(3600);
        sessionCookie.setPath("/");
        response.addCookie(sessionCookie);
    }

    private String generateSessionID(){
        return UUID.randomUUID().toString();
    }

    private void setLoggedInUsers(String sessionID, String email){
        loggedInUsers.put(sessionID, email);
    }
}
