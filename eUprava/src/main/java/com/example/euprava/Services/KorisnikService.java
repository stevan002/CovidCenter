package com.example.euprava.Services;

import com.example.euprava.Models.EUloga;
import com.example.euprava.Models.Korisnik;
import jakarta.servlet.http.Cookie;

import java.util.List;

public interface KorisnikService {
    Korisnik findOne(Long id);
    Korisnik findOneByEmail(String email);
    Korisnik findOneByEmailAndPassword(String email, String lozinka);
    List<Korisnik> findAll();
    void save(Korisnik korisnik);
    Korisnik update(Korisnik korisnik);
    Korisnik delete(Long id);
    boolean checkCookies(Cookie[] cookies, EUloga uloga);
    Korisnik checkCookieUser(Cookie[] cookies);
}
