package com.example.euprava.Services;

import com.example.euprava.Models.Korisnik;

import java.util.List;

public interface KorisnikService {
    Korisnik findOne(Long id);
    Korisnik findOneByEmail(String email);
    Korisnik findOneByEmailAndPassword(String email, String lozinka);
    List<Korisnik> findAll();
    Korisnik save(Korisnik korisnik);
    Korisnik update(Korisnik korisnik);
    Korisnik delete(Long id);
}
