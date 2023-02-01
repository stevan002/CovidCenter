package com.example.euprava.Services;

import com.example.euprava.Models.Korisnik;

import java.util.List;

public interface KorisnikService {
    Korisnik findOne(Long id);
    Korisnik findOne(String email);
    Korisnik findOne(String email, String lozinka);
    List<Korisnik> findAll();
    Korisnik save(Korisnik korisnik);
    Korisnik update(Korisnik korisnik);
    Korisnik delete(Long id);
    List<Korisnik> find(String email, String ime, String prezime, String Adresa);
}
